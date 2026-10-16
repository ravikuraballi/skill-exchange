package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val college: String,
    val branch: String,
    val semester: String,
    val bio: String,
    val photoUrl: String = "",
    val karma: Int = 500,
    val reputation: Float = 4.8f,
    val completedExchanges: Int = 0,
    val hoursExchanged: Int = 0,
    val availability: String = "Weekday evenings",
    val isVerified: Boolean = true,
    val isCurrentUser: Boolean = false,
    val offersSummary: String = "",
    val wantsSummary: String = "",
    val matchPercentage: Int = 90
)

@Entity(tableName = "skills")
data class SkillItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val category: String, // "Programming", "Web Dev", "Design", "Data Science", "Math", "Communication"
    val type: String, // "TEACH" or "LEARN"
    val proficiency: String, // "Beginner", "Intermediate", "Advanced"
    val description: String = ""
)

@Entity(tableName = "exchange_requests")
data class ExchangeRequestEntity(
    @PrimaryKey val id: String,
    val reqCode: String, // "#REQ-8492"
    val senderId: String,
    val receiverId: String,
    val senderName: String,
    val senderCollege: String,
    val senderBranchSemester: String,
    val senderPhotoUrl: String = "",
    val receiverName: String,
    val offeredSkills: String, // e.g. "PostgreSQL, Docker"
    val requestedSkills: String, // e.g. "React Native"
    val message: String,
    val scheduledTime: String = "Fridays at 5:00 PM",
    val matchPercentage: Int = 94,
    val status: String, // "PENDING", "ACCEPTED", "DECLINED", "CANCELLED", "COMPLETED"
    val isIncoming: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val conversationId: String, // "conv_marcus", "conv_david", etc.
    val senderId: String,
    val senderName: String,
    val senderPhotoUrl: String = "",
    val content: String,
    val messageType: String = "TEXT", // "TEXT", "CODE", "SESSION_CARD"
    val codeSnippet: String = "",
    val codeFileName: String = "",
    val sessionDate: String = "",
    val sessionTime: String = "",
    val sessionTopic: String = "",
    val sessionDuration: String = "45 min",
    val sessionAccessToken: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = true,
    val isFromMe: Boolean = false
)

@Entity(tableName = "learning_sessions")
data class LearningSessionEntity(
    @PrimaryKey val id: String,
    val topic: String,
    val partnerName: String,
    val partnerPhotoUrl: String = "",
    val date: String,
    val time: String,
    val durationMinutes: Int = 60,
    val roomCode: String = "#SKILL-8024-LIVE",
    val status: String = "CONFIRMED", // "CONFIRMED", "COMPLETED", "CANCELLED"
    val meetingLink: String = "https://skillswap.live/room/8024",
    val notes: String = ""
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val type: String, // "REQUEST", "ACCEPTED", "SESSION", "ACHIEVEMENT"
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val relatedId: String = ""
)
