package com.example.data.repository

import com.example.data.local.dao.SkillSwapDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.ExchangeRequestEntity
import com.example.data.local.entity.LearningSessionEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.SkillItemEntity
import com.example.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SkillSwapRepository(private val dao: SkillSwapDao) {

    val currentUser: Flow<UserEntity?> = dao.getCurrentUser()
    val peers: Flow<List<UserEntity>> = dao.getAllPeers()
    val incomingRequests: Flow<List<ExchangeRequestEntity>> = dao.getIncomingRequests()
    val sentRequests: Flow<List<ExchangeRequestEntity>> = dao.getSentRequests()
    val historyRequests: Flow<List<ExchangeRequestEntity>> = dao.getHistoryRequests()
    val notifications: Flow<List<NotificationEntity>> = dao.getAllNotifications()
    val unreadNotificationCount: Flow<Int> = dao.getUnreadNotificationCount()
    val nextUpcomingSession: Flow<LearningSessionEntity?> = dao.getNextUpcomingSession()

    fun getUserSkills(userId: String): Flow<List<SkillItemEntity>> = dao.getSkillsForUser(userId)

    fun getChatMessages(convId: String): Flow<List<ChatMessageEntity>> = dao.getMessagesForConversation(convId)

    suspend fun ensureInitialData() {
        try {
            if (dao.getUserCount() == 0) {
                com.example.data.local.SkillSwapDatabase.populateInitialData(dao)
            }
        } catch (e: Exception) {
            android.util.Log.e("SkillSwapRepo", "Error initializing data", e)
        }
    }

    suspend fun proposeSwap(
        receiver: UserEntity,
        offeredSkill: String,
        requestedSkill: String,
        message: String,
        timeSlot: String
    ): ExchangeRequestEntity {
        val reqId = "req_${UUID.randomUUID().toString().take(6)}"
        val code = "#REQ-${(1000..9999).random()}"
        val request = ExchangeRequestEntity(
            id = reqId,
            reqCode = code,
            senderId = "user_ananya",
            receiverId = receiver.id,
            senderName = receiver.name,
            senderCollege = receiver.college,
            senderBranchSemester = receiver.semester,
            senderPhotoUrl = receiver.photoUrl,
            receiverName = "Ananya Sharma",
            offeredSkills = offeredSkill,
            requestedSkills = requestedSkill,
            message = message,
            scheduledTime = timeSlot,
            matchPercentage = receiver.matchPercentage,
            status = "PENDING",
            isIncoming = false,
            timestamp = System.currentTimeMillis()
        )
        dao.insertRequest(request)

        // Generate confirmation notification
        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(6)}",
                title = "Swap Proposal Dispatched",
                body = "Sent proposal to ${receiver.name} for $requestedSkill ⇄ $offeredSkill",
                type = "REQUEST",
                timestamp = System.currentTimeMillis(),
                isRead = false,
                relatedId = reqId
            )
        )
        return request
    }

    suspend fun acceptRequest(request: ExchangeRequestEntity) {
        dao.updateRequestStatus(request.id, "ACCEPTED")

        // Create or activate a session / chat message confirmation
        val convId = "conv_${request.senderName.lowercase().replace(" ", "_")}"
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(6)}",
                conversationId = convId,
                senderId = "system",
                senderName = "SkillSwap System",
                content = "Swap Confirmed! You are now connected with ${request.senderName}. Schedule sessions and exchange knowledge.",
                messageType = "TEXT",
                timestamp = System.currentTimeMillis(),
                isFromMe = false
            )
        )

        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(6)}",
                title = "Swap Activated with ${request.senderName}!",
                body = "Private workspace and direct scheduling unlocked.",
                type = "ACCEPTED",
                timestamp = System.currentTimeMillis(),
                isRead = false,
                relatedId = request.id
            )
        )
    }

    suspend fun declineRequest(requestId: String) {
        dao.updateRequestStatus(requestId, "DECLINED")
    }

    suspend fun cancelRequest(requestId: String) {
        dao.updateRequestStatus(requestId, "CANCELLED")
    }

    suspend fun sendTextMessage(convId: String, text: String, senderName: String) {
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(6)}",
                conversationId = convId,
                senderId = "user_ananya",
                senderName = senderName,
                content = text,
                messageType = "TEXT",
                timestamp = System.currentTimeMillis(),
                isFromMe = true
            )
        )
    }

    suspend fun sendPeerReply(
        convId: String,
        senderId: String,
        senderName: String,
        senderPhotoUrl: String,
        content: String
    ) {
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(6)}",
                conversationId = convId,
                senderId = senderId,
                senderName = senderName,
                senderPhotoUrl = senderPhotoUrl,
                content = content,
                messageType = "TEXT",
                timestamp = System.currentTimeMillis(),
                isFromMe = false
            )
        )
    }

    suspend fun sendCodeSnippet(convId: String, fileName: String, code: String, senderName: String) {
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(6)}",
                conversationId = convId,
                senderId = "user_ananya",
                senderName = senderName,
                content = "Shared code snippet: $fileName",
                messageType = "CODE",
                codeFileName = fileName,
                codeSnippet = code,
                timestamp = System.currentTimeMillis(),
                isFromMe = true
            )
        )
    }

    suspend fun addSkill(
        userId: String,
        title: String,
        category: String,
        type: String,
        proficiency: String,
        description: String
    ) {
        val skill = SkillItemEntity(
            id = "sk_${UUID.randomUUID().toString().take(6)}",
            userId = userId,
            title = title,
            category = category,
            type = type,
            proficiency = proficiency,
            description = description
        )
        dao.insertSkill(skill)
    }

    suspend fun deleteSkill(id: String) {
        dao.deleteSkill(id)
    }

    suspend fun updateUser(user: UserEntity) {
        dao.updateUser(user)
    }

    suspend fun markNotificationAsRead(id: String) {
        dao.markNotificationAsRead(id)
    }

    suspend fun markAllNotificationsAsRead() {
        dao.markAllNotificationsAsRead()
    }

    suspend fun scheduleSession(
        topic: String,
        partnerName: String,
        date: String,
        time: String,
        durationMinutes: Int
    ) {
        val session = LearningSessionEntity(
            id = "sess_${UUID.randomUUID().toString().take(6)}",
            topic = topic,
            partnerName = partnerName,
            date = date,
            time = time,
            durationMinutes = durationMinutes,
            roomCode = "#SKILL-${(1000..9999).random()}-LIVE",
            status = "CONFIRMED",
            meetingLink = "https://skillswap.live/room/${(1000..9999).random()}",
            notes = "Peer session topic: $topic"
        )
        dao.insertSession(session)

        dao.insertNotification(
            NotificationEntity(
                id = "notif_${UUID.randomUUID().toString().take(6)}",
                title = "New Session Scheduled: $topic",
                body = "Confirmed with $partnerName on $date at $time.",
                type = "SESSION",
                timestamp = System.currentTimeMillis(),
                isRead = false,
                relatedId = session.id
            )
        )
    }
}
