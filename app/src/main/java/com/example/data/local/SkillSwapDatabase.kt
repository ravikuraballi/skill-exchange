package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.SkillSwapDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.ExchangeRequestEntity
import com.example.data.local.entity.LearningSessionEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.SkillItemEntity
import com.example.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        SkillItemEntity::class,
        ExchangeRequestEntity::class,
        ChatMessageEntity::class,
        LearningSessionEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SkillSwapDatabase : RoomDatabase() {

    abstract fun skillSwapDao(): SkillSwapDao

    companion object {
        @Volatile
        private var INSTANCE: SkillSwapDatabase? = null

        fun getDatabase(context: Context): SkillSwapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SkillSwapDatabase::class.java,
                    "skillswap_db"
                )
                .fallbackToDestructiveMigration(true)
                .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateInitialData(dao: SkillSwapDao) {
            // Seed Current User (Ananya Sharma)
            val currentUser = UserEntity(
                id = "user_ananya",
                name = "Ananya Sharma",
                email = "ananya.sharma@statetech.edu",
                college = "State Tech University",
                branch = "Computer Science",
                semester = "6th Sem",
                bio = "Building high-performance mobile apps & distributed backend systems. Looking to learn Rust & Systems Design while sharing Python and React.",
                photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA9cdKNYpSu6bFO5Ndgzn6dG3evLMtubNmKPb1mbNe-qvOEBQgcYN_b1xkY6g6aF7H8uS0tbA2JH_Q2lnWWSnzahGw30Sa2QpcFD07-7NMYR7mON2Kmma0ufTbPLUzLPmrTAcjhfWCxBdxagb-8y7kWj3jPooMlvQD0kp0YwuRrWSUePfWj66S0bCiZbxvk7On_I4zupB-zOTWU_OVeybMl2F75YIJ7V0XjybvTK7-GepBxwqXIIC96",
                karma = 740,
                reputation = 4.9f,
                completedExchanges = 4,
                hoursExchanged = 18,
                availability = "Weekday evenings",
                isVerified = true,
                isCurrentUser = true,
                offersSummary = "Python, DSA, React Native",
                wantsSummary = "Rust, System Design"
            )

            // Seed Peers
            val peers = listOf(
                UserEntity(
                    id = "user_marcus",
                    name = "Marcus Vance",
                    email = "marcus.v@stanford.edu",
                    college = "Stanford University",
                    branch = "Electrical Engineering",
                    semester = "EE 4th Sem",
                    bio = "Low-level systems enthusiast, writing embedded firmware and microcontrollers in Rust. Seeking guidance on Python scripting & PyTorch.",
                    photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAxeIA5BwEbpWePsYcT0c1tDLWTP62Kj4nFuEGdIBnuj97H4taihhPTH_D5sxBA4CNJ_DtujgzF-dw3zffOwJqqPxXnkcgyifHdNWl1f7dtp7l39t3zQAKFKaBdJC3vaX8qdZhwax8gtjqvO-rjpPwM9D-w7waKGL_bcVgsaAF7qCjAvglhaMQ047E7qknAkxKc0aoF6Q7QMHS9Q1Li0eOMVzUSt4FzSbSsbvy1ajMI0L68c6G3ZU4F",
                    karma = 890,
                    reputation = 4.9f,
                    completedExchanges = 12,
                    hoursExchanged = 36,
                    availability = "Weekend afternoons",
                    isVerified = true,
                    isCurrentUser = false,
                    offersSummary = "Rust (Adv), Embedded C",
                    wantsSummary = "Python (Match), PyTorch",
                    matchPercentage = 98
                ),
                UserEntity(
                    id = "user_priya",
                    name = "Priya Patel",
                    email = "priya.patel@gatech.edu",
                    college = "Georgia Tech",
                    branch = "Design & HCI",
                    semester = "Design 5th Sem",
                    bio = "Product designer passionate about accessibility, Figma design tokens, and scalable UI systems. Eager to master frontend React development.",
                    photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBkktR2RXqlIjA1xEw3aGmU9KdU_2cmEnT7WmQTKqN4RJ-G5Nju4scxJBwJ0CmcHetNxjPeZDpoP4MchiswDWcG-TarAoe57kHqABWbiig4WmUrFD1Y4XRG6THx4PDhSyYfciZELbw72MZxDVbE5pTkQhYUvJrXfnr98ew-zUvd41k6eYWDnr_M0_9N9nUMhFztbvGM9QEz9qG_776c9dsWkL1sspbaQn_eoylZJAk0B7kQPfi77M6h",
                    karma = 920,
                    reputation = 5.0f,
                    completedExchanges = 15,
                    hoursExchanged = 42,
                    availability = "Weekday evenings",
                    isVerified = true,
                    isCurrentUser = false,
                    offersSummary = "Figma (Adv), Design Systems",
                    wantsSummary = "React (Match), Tailwind CSS",
                    matchPercentage = 92
                ),
                UserEntity(
                    id = "user_elena",
                    name = "Elena Rostova",
                    email = "elena.r@mit.edu",
                    college = "MIT",
                    branch = "Applied Mathematics",
                    semester = "Math 6th Sem",
                    bio = "Math & theoretical computer science student. Teaching linear algebra, vector spaces, and probability. Wanting to polish LeetCode DSA.",
                    photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDgfiZwlQRfthGpI8ECNEsjgNx-Wn4RET440mhJ2DoztyTjo3cFJZq3pekrJXgCjMntwDRn2Xflog9pgD-i-1LLlUMVBUpougEtikik5OJLI_UqIfVeqc0tUtB7R6dI861Ckw2eDLM2j8FLVZ2kqrvMy8KY2yhP1H-Uaqds2hQJsD4fhAQPbCFJ8FpalcF5-0Yk7KB6ywRy-S9fiy_O4Tf8qq-oL-EBiO3ifiotecEjZlAS96cfMnsJ",
                    karma = 810,
                    reputation = 4.8f,
                    completedExchanges = 8,
                    hoursExchanged = 24,
                    availability = "Online / Flexible",
                    isVerified = true,
                    isCurrentUser = false,
                    offersSummary = "Linear Algebra, Statistics",
                    wantsSummary = "LeetCode DSA (Match), C++",
                    matchPercentage = 88
                ),
                UserEntity(
                    id = "user_liam",
                    name = "Liam Chen",
                    email = "liam.chen@statetech.edu",
                    college = "State Tech '25",
                    branch = "CS & Systems",
                    semester = "CS 8th Sem",
                    bio = "Senior backend engineer specializing in high-throughput microservices and distributed caching. Swap for React & TypeScript.",
                    photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCB8797vpI7_IYD8bITgD9JR3ZWy5KdRRkoILo6cA7eNHdZrmjaZmAMpfUhWYMagN_jI9bW1Hiwbbd1D98ZMR73hUSelTqJBpeFJjAE2lRZ1nBToipYY6wJ30hms6xIShmPxFGcbIILoG580xxRDeCVmhFkWozlFtmWf0po4U9TDcdSRd7abt3IOQR3HqofpQI0XUgp4Jmu6bjql7_vuYjTPSCjZrpTl1cWZwh5y5y41dt4yyUUHfQH",
                    karma = 1150,
                    reputation = 4.95f,
                    completedExchanges = 20,
                    hoursExchanged = 65,
                    availability = "Flexible weekend mornings",
                    isVerified = true,
                    isCurrentUser = false,
                    offersSummary = "System Design, Microservices",
                    wantsSummary = "React & TypeScript",
                    matchPercentage = 96
                ),
                UserEntity(
                    id = "user_maya",
                    name = "Maya Lin",
                    email = "maya.lin@statetech.edu",
                    college = "State Tech University",
                    branch = "Design & HCI",
                    semester = "4th Sem",
                    bio = "Hackathon enthusiast! Teaching Figma heuristics and rapid wireframing in exchange for Python scraping.",
                    photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH",
                    karma = 670,
                    reputation = 4.85f,
                    completedExchanges = 6,
                    hoursExchanged = 16,
                    availability = "Thursday evenings (3 hrs/wk)",
                    isVerified = true,
                    isCurrentUser = false,
                    offersSummary = "Figma Design Systems, UX Heuristics",
                    wantsSummary = "Python Scripting, Data Scraping",
                    matchPercentage = 92
                )
            )

            dao.insertUser(currentUser)
            dao.insertUsers(peers)

            // Seed Skills for Current User
            val ananyaSkills = listOf(
                SkillItemEntity(
                    id = "sk_ananya_1",
                    userId = "user_ananya",
                    title = "Python Programming",
                    category = "Programming",
                    type = "TEACH",
                    proficiency = "Advanced",
                    description = "FastAPI, async tasks, data pipelines and clean architecture."
                ),
                SkillItemEntity(
                    id = "sk_ananya_2",
                    userId = "user_ananya",
                    title = "Data Structures & Algorithms",
                    category = "CS & Engineering",
                    type = "TEACH",
                    proficiency = "Advanced",
                    description = "Trees, Dynamic Programming, Graph Traversal, and LeetCode patterns."
                ),
                SkillItemEntity(
                    id = "sk_ananya_3",
                    userId = "user_ananya",
                    title = "React & TypeScript",
                    category = "Web Development",
                    type = "TEACH",
                    proficiency = "Intermediate",
                    description = "Modern hooks, state management, and component architecture."
                ),
                SkillItemEntity(
                    id = "sk_ananya_4",
                    userId = "user_ananya",
                    title = "Rust Systems Programming",
                    category = "Programming",
                    type = "LEARN",
                    proficiency = "Beginner",
                    description = "Mastering the borrow checker, ownership semantics, and lifetimes."
                ),
                SkillItemEntity(
                    id = "sk_ananya_5",
                    userId = "user_ananya",
                    title = "Distributed System Design",
                    category = "CS & Engineering",
                    type = "LEARN",
                    proficiency = "Beginner",
                    description = "Load balancers, CAP theorem, distributed caching, and microservices."
                )
            )
            dao.insertSkills(ananyaSkills)

            // Seed Requests
            val requests = listOf(
                ExchangeRequestEntity(
                    id = "req_8492",
                    reqCode = "#REQ-8492",
                    senderId = "user_alex",
                    receiverId = "user_ananya",
                    senderName = "Alex Rivera",
                    senderCollege = "UC Berkeley",
                    senderBranchSemester = "CS 3rd Sem",
                    senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDmTvVltswXSQxUdo-OFo9EV_9XNwfBx6RehKvurtf1kt5SbmzN5aXeXMnH3TVCSI8NakQEGHdReDkXi7HFcvYDtdMjDwgUdXiZ8piNnQQpFhLSBtzaiZhiyLhhZwBS9PqbdiSnx4ZwTLxlYCC3sB_OHgZHsjQe0w6wrapslXIcIkalzpmcOIXmRjho7-IIZXDl0zvDXYfM_HnpdM48HCBmQ2_Qx9piuvSEKoHJUIw2dlv72p0lih5",
                    receiverName = "Ananya Sharma",
                    offeredSkills = "PostgreSQL, Docker",
                    requestedSkills = "React Native",
                    message = "Hey Ananya! Loved your open-source Flutter project. I have 2 years of backend DB experience and would love to swap 1-hr weekly sessions!",
                    scheduledTime = "Fridays at 5:00 PM",
                    matchPercentage = 94,
                    status = "PENDING",
                    isIncoming = true,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 18
                ),
                ExchangeRequestEntity(
                    id = "req_8493",
                    reqCode = "#REQ-8493",
                    senderId = "user_sophia",
                    receiverId = "user_ananya",
                    senderName = "Sophia Taylor",
                    senderCollege = "Columbia",
                    senderBranchSemester = "Data Science",
                    senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB0_OzBAnl5vR-Tl_G3Pz66H6bNmlkjuzW_XtbmUWVZvS-M652gKg5pmO4AUoaZa8pAJpumsWjbtDoHJxsPFGp0PoztTSsMDE3h9nDAguK4BzaK81MWWw_4HB1IQ64eml8F5TFaimvBELo5zb8wkRCWbPhlPmze_-9L67HYGEJSmyxwUYbdMQnG2KSlN_sZFxuCngAmuUNE3KWzRnoFpjw7pKFlQfl1f1pVKMX4g4HTnoHsVepRKQyj",
                    receiverName = "Ananya Sharma",
                    offeredSkills = "Python Pandas & Cleaning",
                    requestedSkills = "UI Graphic Design",
                    message = "Hi Ananya! I need help designing interactive Figma wireframes for my data project, and can teach you Pandas, Numpy, and data cleaning in return.",
                    scheduledTime = "Flexible • Remote",
                    matchPercentage = 88,
                    status = "PENDING",
                    isIncoming = true,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 120
                ),
                ExchangeRequestEntity(
                    id = "req_7910",
                    reqCode = "#REQ-7910",
                    senderId = "user_ananya",
                    receiverId = "user_david",
                    senderName = "David Kim",
                    senderCollege = "Stanford University",
                    senderBranchSemester = "Symbolic Systems",
                    senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDoaKCbrPe81v0rw1KXT_sttPkZrfDFPGFAvRkzsM6QPO-ImDp-W1DKuns_-TNurdBA-ZQ8iPFhJSMAI4oLGYsLJmtq04qcYphyYAr6kvoqvG5h2drWIc_DFZhvQItmbAjcmzTyTXmBlx8PVwRplt1Ll0s4iFTi2WsJWzOXVPqHm416CapZp4XVu-YMkGcC2KViK1_hv5RL2tqGWC3chHGknoz017gK-4ntQyTsfqvXAObm8JJTOBIN",
                    receiverName = "David Kim",
                    offeredSkills = "TypeScript & Next.js",
                    requestedSkills = "Figma Design Systems",
                    message = "Let's trade frontend TypeScript expertise for design system architecture!",
                    scheduledTime = "Wednesdays at 4:00 PM",
                    matchPercentage = 96,
                    status = "ACCEPTED",
                    isIncoming = false,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24
                ),
                ExchangeRequestEntity(
                    id = "req_8311",
                    reqCode = "#REQ-8311",
                    senderId = "user_ananya",
                    receiverId = "user_maya",
                    senderName = "Maya Lin",
                    senderCollege = "MIT",
                    senderBranchSemester = "AI & Robotics",
                    senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH",
                    receiverName = "Maya Lin",
                    offeredSkills = "PyTorch Deep Learning",
                    requestedSkills = "UI Prototyping",
                    message = "Offer: PyTorch Deep Learning for UI Prototyping.",
                    scheduledTime = "Thursday evenings",
                    matchPercentage = 92,
                    status = "PENDING",
                    isIncoming = false,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 18
                ),
                ExchangeRequestEntity(
                    id = "req_7001",
                    reqCode = "#REQ-7001",
                    senderId = "user_liam_vance",
                    receiverId = "user_ananya",
                    senderName = "Liam Vance",
                    senderCollege = "State Tech",
                    senderBranchSemester = "Comp Eng",
                    senderPhotoUrl = "",
                    receiverName = "Ananya Sharma",
                    offeredSkills = "Rust Systems Programming",
                    requestedSkills = "UI UX Design",
                    message = "Great 4 completed sessions! Exchanged foundational Rust concepts for mobile UI designs.",
                    scheduledTime = "Cycle concluded Oct 24",
                    matchPercentage = 98,
                    status = "COMPLETED",
                    isIncoming = true,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 72
                ),
                ExchangeRequestEntity(
                    id = "req_7002",
                    reqCode = "#REQ-7002",
                    senderId = "user_chen",
                    receiverId = "user_ananya",
                    senderName = "Chen Wei",
                    senderCollege = "UC San Diego",
                    senderBranchSemester = "Cloud Systems",
                    senderPhotoUrl = "",
                    receiverName = "Ananya Sharma",
                    offeredSkills = "Kubernetes Cluster Architecture",
                    requestedSkills = "React Native",
                    message = "Schedule mismatch.",
                    scheduledTime = "Declined Oct 12",
                    matchPercentage = 75,
                    status = "DECLINED",
                    isIncoming = true,
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 120
                )
            )
            dao.insertRequests(requests)

            // Seed Chat Messages for conversation with Marcus Vance
            val rustCode = """fn main() {
    // Allocates heap memory for string buffer
    let s = String::from("SkillSwap");
    
    calculate_length(&s); // Pass immutable borrow
    
    println!("Owner still retains: {}", s);
}

fn calculate_length(ref_str: &String) -> usize {
    ref_str.len()
}"""

            val marcusMessages = listOf(
                ChatMessageEntity(
                    id = "msg_1",
                    conversationId = "conv_marcus",
                    senderId = "user_marcus",
                    senderName = "Marcus",
                    senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB-YSj7SH-oWvkZW7uUEwvuxFWw6j8igyoG68I060tcUMn2fWpgq4L3aufOwBfk7Dqkv_Ovl9Cq0NrkqS0gX79ru_qZjVaqfyQ3Q4G3pDBrBlVyGRwZI5MUr5SG7Uqsro-WAALIxTSrcAVl-sVSx7ruR2z_xen5kTP7nZabZRu_yhGbHtRAmwKY1Yy11tBl22PJnOAQ9qJVCDGmParrwr--tnySpH2B155ODvZRc-nMouNnR26lXRwU",
                    content = "Hey Ananya! Here is the memory safety snippet we discussed for our Rust session tomorrow:",
                    messageType = "TEXT",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 32,
                    isFromMe = false
                ),
                ChatMessageEntity(
                    id = "msg_2",
                    conversationId = "conv_marcus",
                    senderId = "user_marcus",
                    senderName = "Marcus",
                    senderPhotoUrl = "",
                    content = rustCode,
                    messageType = "CODE",
                    codeSnippet = rustCode,
                    codeFileName = "ownership_borrow.rs",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 31,
                    isFromMe = false
                ),
                ChatMessageEntity(
                    id = "msg_3",
                    conversationId = "conv_marcus",
                    senderId = "user_ananya",
                    senderName = "Ananya",
                    content = "Awesome! I drafted the Python FastAPI starter repo for you too. Can we schedule 45 mins?",
                    messageType = "TEXT",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 28,
                    isFromMe = true
                ),
                ChatMessageEntity(
                    id = "msg_4",
                    conversationId = "conv_marcus",
                    senderId = "user_marcus",
                    senderName = "Marcus",
                    content = "Rust Fundamentals & Memory Safety session",
                    messageType = "SESSION_CARD",
                    sessionTopic = "Rust Fundamentals & Memory Safety",
                    sessionDate = "Thu, Oct 26",
                    sessionTime = "4:00 - 4:45 PM",
                    sessionDuration = "45 min",
                    sessionAccessToken = "sk-live-peer-rust99",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 25,
                    isFromMe = false
                )
            )
            dao.insertMessages(marcusMessages)

            // Seed Upcoming Learning Session
            val upcomingSession = LearningSessionEntity(
                id = "session_docker_1",
                topic = "Docker & Kubernetes Lab",
                partnerName = "David Kim",
                partnerPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDoaKCbrPe81v0rw1KXT_sttPkZrfDFPGFAvRkzsM6QPO-ImDp-W1DKuns_-TNurdBA-ZQ8iPFhJSMAI4oLGYsLJmtq04qcYphyYAr6kvoqvG5h2drWIc_DFZhvQItmbAjcmzTyTXmBlx8PVwRplt1Ll0s4iFTi2WsJWzOXVPqHm416CapZp4XVu-YMkGcC2KViK1_hv5RL2tqGWC3chHGknoz017gK-4ntQyTsfqvXAObm8JJTOBIN",
                date = "Tomorrow",
                time = "4:00 PM",
                durationMinutes = 60,
                roomCode = "#SKILL-8024-LIVE",
                status = "CONFIRMED",
                meetingLink = "https://skillswap.live/room/8024",
                notes = "Focus on multi-stage Docker builds and Helm chart configuration."
            )
            dao.insertSession(upcomingSession)

            // Seed Notifications
            val notifications = listOf(
                NotificationEntity(
                    id = "notif_1",
                    title = "New Swap Request from Alex Rivera",
                    body = "Offered PostgreSQL & Docker in exchange for React Native.",
                    type = "REQUEST",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 18,
                    isRead = false,
                    relatedId = "req_8492"
                ),
                NotificationEntity(
                    id = "notif_2",
                    title = "David Kim accepted your swap offer!",
                    body = "Virtual study room #SKILL-8024-LIVE is now confirmed.",
                    type = "ACCEPTED",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2,
                    isRead = false,
                    relatedId = "req_7910"
                ),
                NotificationEntity(
                    id = "notif_3",
                    title = "Upcoming Session Tomorrow at 4:00 PM",
                    body = "Docker & Kubernetes Lab with David Kim starts in 24 hours.",
                    type = "SESSION",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 5,
                    isRead = true,
                    relatedId = "session_docker_1"
                ),
                NotificationEntity(
                    id = "notif_4",
                    title = "Karma Level 3 Milestone Achieved!",
                    body = "You reached 740 Karma points for teaching Python to 4 peers.",
                    type = "ACHIEVEMENT",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24,
                    isRead = true
                )
            )
            dao.insertNotifications(notifications)
        }
    }
}
