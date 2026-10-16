package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.ExchangeRequestEntity
import com.example.data.local.entity.LearningSessionEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.SkillItemEntity
import com.example.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillSwapDao {

    // --- Users ---
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    suspend fun getCurrentUserSync(): UserEntity?

    @Query("SELECT * FROM users WHERE isCurrentUser = 0 ORDER BY matchPercentage DESC")
    fun getAllPeers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET isCurrentUser = CASE WHEN id = :userId THEN 1 ELSE 0 END")
    suspend fun setCurrentUser(userId: String)

    // --- Skills ---
    @Query("SELECT * FROM skills WHERE userId = :userId")
    fun getSkillsForUser(userId: String): Flow<List<SkillItemEntity>>

    @Query("SELECT * FROM skills")
    fun getAllSkills(): Flow<List<SkillItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(skill: SkillItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<SkillItemEntity>)

    @Query("DELETE FROM skills WHERE id = :id")
    suspend fun deleteSkill(id: String)

    // --- Exchange Requests ---
    @Query("SELECT * FROM exchange_requests WHERE isIncoming = 1 AND status = 'PENDING' ORDER BY timestamp DESC")
    fun getIncomingRequests(): Flow<List<ExchangeRequestEntity>>

    @Query("SELECT * FROM exchange_requests WHERE isIncoming = 0 AND (status = 'PENDING' OR status = 'ACCEPTED') ORDER BY timestamp DESC")
    fun getSentRequests(): Flow<List<ExchangeRequestEntity>>

    @Query("SELECT * FROM exchange_requests WHERE status IN ('COMPLETED', 'DECLINED', 'CANCELLED') ORDER BY timestamp DESC")
    fun getHistoryRequests(): Flow<List<ExchangeRequestEntity>>

    @Query("SELECT * FROM exchange_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<ExchangeRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: ExchangeRequestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequests(requests: List<ExchangeRequestEntity>)

    @Query("UPDATE exchange_requests SET status = :status WHERE id = :id")
    suspend fun updateRequestStatus(id: String, status: String)

    @Query("DELETE FROM exchange_requests WHERE id = :id")
    suspend fun deleteRequest(id: String)

    // --- Chat Messages ---
    @Query("SELECT * FROM chat_messages WHERE conversationId = :convId ORDER BY timestamp ASC")
    fun getMessagesForConversation(convId: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<ChatMessageEntity>)

    @Query("UPDATE chat_messages SET isRead = 1 WHERE conversationId = :convId")
    suspend fun markMessagesAsRead(convId: String)

    // --- Learning Sessions ---
    @Query("SELECT * FROM learning_sessions ORDER BY date ASC, time ASC")
    fun getAllSessions(): Flow<List<LearningSessionEntity>>

    @Query("SELECT * FROM learning_sessions WHERE status = 'CONFIRMED' LIMIT 1")
    fun getNextUpcomingSession(): Flow<LearningSessionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: LearningSessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<LearningSessionEntity>)

    @Query("UPDATE learning_sessions SET status = :status WHERE id = :id")
    suspend fun updateSessionStatus(id: String, status: String)

    // --- Notifications ---
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun getUnreadNotificationCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markNotificationAsRead(id: String)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsAsRead()
}
