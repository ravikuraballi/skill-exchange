package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.ExchangeRequestEntity
import com.example.data.local.entity.LearningSessionEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.SkillItemEntity
import com.example.data.local.entity.UserEntity
import com.example.data.repository.SkillSwapRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    HOME, DISCOVER, REQUESTS, CHAT, PROFILE
}

enum class SortMode {
    HIGHEST_FIT, FASTEST_RESPONSE, TOP_RATED
}

class SkillSwapViewModel(private val repository: SkillSwapRepository) : ViewModel() {

    // Current navigation tab
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    init {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            repository.ensureInitialData()
        }
    }

    // Transient UI toast / banner events
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    companion object {
        val defaultUser = UserEntity(
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
    }

    // Base flows from repository
    val currentUser: StateFlow<UserEntity?> = repository.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = defaultUser
    )

    // Chat peer typing simulation state
    private val _isPeerTyping = MutableStateFlow(false)
    val isPeerTyping: StateFlow<Boolean> = _isPeerTyping.asStateFlow()

    private val _rawPeers = repository.peers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val incomingRequests: StateFlow<List<ExchangeRequestEntity>> = repository.incomingRequests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val sentRequests: StateFlow<List<ExchangeRequestEntity>> = repository.sentRequests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val historyRequests: StateFlow<List<ExchangeRequestEntity>> = repository.historyRequests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val notifications: StateFlow<List<NotificationEntity>> = repository.notifications.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val unreadNotificationCount: StateFlow<Int> = repository.unreadNotificationCount.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val upcomingSession: StateFlow<LearningSessionEntity?> = repository.nextUpcomingSession.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val userSkills: StateFlow<List<SkillItemEntity>> = repository.getUserSkills("user_ananya").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.getChatMessages("conv_marcus").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Discover Screen filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilterChip = MutableStateFlow("Exact Reciprocal")
    val selectedFilterChip: StateFlow<String> = _selectedFilterChip.asStateFlow()

    private val _sortMode = MutableStateFlow(SortMode.HIGHEST_FIT)
    val sortMode: StateFlow<SortMode> = _sortMode.asStateFlow()

    // Filtered & sorted peers list
    val filteredPeers: StateFlow<List<UserEntity>> = combine(
        _rawPeers,
        _searchQuery,
        _selectedFilterChip,
        _sortMode
    ) { peers, query, filter, sort ->
        var list = peers

        // Text search filter
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter { peer ->
                peer.name.lowercase().contains(q) ||
                peer.college.lowercase().contains(q) ||
                peer.branch.lowercase().contains(q) ||
                peer.offersSummary.lowercase().contains(q) ||
                peer.wantsSummary.lowercase().contains(q)
            }
        }

        // Chip filter
        when (filter) {
            "Exact Reciprocal" -> {
                // Focus on high matches with clear cross-matches
                list = list.filter { it.matchPercentage >= 85 }
            }
            "CS & Engineering" -> {
                list = list.filter { it.branch.contains("CS", ignoreCase = true) || it.branch.contains("EE", ignoreCase = true) || it.branch.contains("Engineering", ignoreCase = true) }
            }
            "Advanced Only" -> {
                list = list.filter { it.offersSummary.contains("Adv", ignoreCase = true) || it.reputation >= 4.9f }
            }
            "Online / Hybrid" -> {
                list = list.filter { it.availability.contains("Online", ignoreCase = true) || it.availability.contains("Flexible", ignoreCase = true) }
            }
        }

        // Sorting
        when (sort) {
            SortMode.HIGHEST_FIT -> list.sortedByDescending { it.matchPercentage }
            SortMode.FASTEST_RESPONSE -> list.sortedByDescending { it.karma }
            SortMode.TOP_RATED -> list.sortedByDescending { it.reputation }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Dialog & Sheet States
    private val _selectedPeerForProposal = MutableStateFlow<UserEntity?>(null)
    val selectedPeerForProposal: StateFlow<UserEntity?> = _selectedPeerForProposal.asStateFlow()

    private val _isFilterSheetOpen = MutableStateFlow(false)
    val isFilterSheetOpen: StateFlow<Boolean> = _isFilterSheetOpen.asStateFlow()

    private val _isNotificationDrawerOpen = MutableStateFlow(false)
    val isNotificationDrawerOpen: StateFlow<Boolean> = _isNotificationDrawerOpen.asStateFlow()

    private val _isAiDrawerOpen = MutableStateFlow(false)
    val isAiDrawerOpen: StateFlow<Boolean> = _isAiDrawerOpen.asStateFlow()

    private val _isVideoCallActive = MutableStateFlow(false)
    val isVideoCallActive: StateFlow<Boolean> = _isVideoCallActive.asStateFlow()

    private val _isScheduleSessionOpen = MutableStateFlow(false)
    val isScheduleSessionOpen: StateFlow<Boolean> = _isScheduleSessionOpen.asStateFlow()

    private val _isAddSkillDialogOpen = MutableStateFlow(false)
    val isAddSkillDialogOpen: StateFlow<Boolean> = _isAddSkillDialogOpen.asStateFlow()

    private val _isEditProfileOpen = MutableStateFlow(false)
    val isEditProfileOpen: StateFlow<Boolean> = _isEditProfileOpen.asStateFlow()

    private val _isAvatarPickerOpen = MutableStateFlow(false)
    val isAvatarPickerOpen: StateFlow<Boolean> = _isAvatarPickerOpen.asStateFlow()

    private val _selectedRequestForDetails = MutableStateFlow<ExchangeRequestEntity?>(null)
    val selectedRequestForDetails: StateFlow<ExchangeRequestEntity?> = _selectedRequestForDetails.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilterChip(chip: String) {
        _selectedFilterChip.value = if (_selectedFilterChip.value == chip) "All" else chip
    }

    fun setSortMode(mode: SortMode) {
        _sortMode.value = mode
    }

    fun openFilterSheet(open: Boolean) {
        _isFilterSheetOpen.value = open
    }

    fun openNotificationDrawer(open: Boolean) {
        _isNotificationDrawerOpen.value = open
        if (open) {
            viewModelScope.launch {
                repository.markAllNotificationsAsRead()
            }
        }
    }

    fun openAiDrawer(open: Boolean) {
        _isAiDrawerOpen.value = open
    }

    fun openVideoCall(open: Boolean) {
        _isVideoCallActive.value = open
    }

    fun openScheduleSession(open: Boolean) {
        _isScheduleSessionOpen.value = open
    }

    fun openAddSkillDialog(open: Boolean) {
        _isAddSkillDialogOpen.value = open
    }

    fun openEditProfile(open: Boolean) {
        _isEditProfileOpen.value = open
    }

    fun openAvatarPicker(open: Boolean) {
        _isAvatarPickerOpen.value = open
    }

    fun openProposeSwap(peer: UserEntity?) {
        _selectedPeerForProposal.value = peer
    }

    fun openRequestDetails(request: ExchangeRequestEntity?) {
        _selectedRequestForDetails.value = request
    }

    fun submitSwapProposal(
        peer: UserEntity,
        offeredSkill: String,
        requestedSkill: String,
        message: String,
        timeSlot: String
    ) {
        viewModelScope.launch {
            repository.proposeSwap(
                receiver = peer,
                offeredSkill = offeredSkill,
                requestedSkill = requestedSkill,
                message = message,
                timeSlot = timeSlot
            )
            _selectedPeerForProposal.value = null
            _toastEvent.emit("Swap proposal sent to ${peer.name}!")
        }
    }

    fun acceptRequest(request: ExchangeRequestEntity) {
        viewModelScope.launch {
            repository.acceptRequest(request)
            _selectedRequestForDetails.value = null
            _toastEvent.emit("Swap confirmed with ${request.senderName}! Workspace unlocked.")
        }
    }

    fun declineRequest(requestId: String) {
        viewModelScope.launch {
            repository.declineRequest(requestId)
            _selectedRequestForDetails.value = null
            _toastEvent.emit("Request declined.")
        }
    }

    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val trimmed = text.trim()
        viewModelScope.launch {
            // 1. Immediately insert user's message
            repository.sendTextMessage("conv_marcus", trimmed, "Ananya")

            // 2. Start Marcus's typing indicator
            _isPeerTyping.value = true
            kotlinx.coroutines.delay(1300)

            // 3. Generate contextual peer response
            val lower = trimmed.lowercase()
            val reply = when {
                lower.contains("hi") || lower.contains("hello") || lower.contains("hey") || lower.contains("sup") || lower.contains("good morning") -> {
                    "Hey Ananya! Great to hear from you. Ready for our Rust ⇄ Python swap! What topic or challenge would you like to dive into today?"
                }
                lower.contains("rust") || lower.contains("borrow") || lower.contains("ownership") || lower.contains("lifetime") || lower.contains("concurrency") -> {
                    "In Rust, the borrow checker ensures you can have either multiple immutable references `&T` or exactly one mutable reference `&mut T` at any time! In our video study session, I can walk you through how lifetimes in structs avoid dangling references. Want to review a code snippet?"
                }
                lower.contains("schedule") || lower.contains("meet") || lower.contains("time") || lower.contains("session") || lower.contains("tomorrow") || lower.contains("call") || lower.contains("video") || lower.contains("room") -> {
                    "Tomorrow at 4:00 PM works perfect for me! We can join the SkillSwap Virtual Study Room (#SKILL-8024-LIVE) directly from the top banner. Let's do 45 mins of Rust and 45 mins of Python!"
                }
                lower.contains("python") || lower.contains("fastapi") || lower.contains("django") || lower.contains("script") || lower.contains("torch") || lower.contains("ml") || lower.contains("model") -> {
                    "Awesome! I'm really looking forward to learning that from you. Could you show me how you structure asynchronous backend routes in FastAPI and data loaders in PyTorch?"
                }
                lower.contains("code") || lower.contains("review") || lower.contains("fn") || lower.contains("impl") || lower.contains("struct") || lower.contains("bug") || lower.contains("error") -> {
                    "Checked out your logic! It looks super solid. In Rust, you can also use pattern matching on `Result<T, E>` or the `?` operator for clean error propagation. Should we test it together in the study room?"
                }
                lower.contains("thanks") || lower.contains("thank you") || lower.contains("cool") || lower.contains("great") || lower.contains("awesome") || lower.contains("perfect") -> {
                    "Anytime! That's what SkillSwap is all about. Collaborating makes complex concepts so much easier. Let me know whenever you'd like to test code together!"
                }
                else -> {
                    "That's a great point! I definitely want to explore that in our exchange. Feel free to share any code snippets or notes, and let's hop into the live study room whenever you're ready!"
                }
            }

            repository.sendPeerReply(
                convId = "conv_marcus",
                senderId = "user_marcus",
                senderName = "Marcus Vance",
                senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAxeIA5BwEbpWePsYcT0c1tDLWTP62Kj4nFuEGdIBnuj97H4taihhPTH_D5sxBA4CNJ_DtujgzF-dw3zffOwJqqPxXnkcgyifHdNWl1f7dtp7l39t3zQAKFKaBdJC3vaX8qdZhwax8gtjqvO-rjpPwM9D-w7waKGL_bcVgsaAF7qCjAvglhaMQ047E7qknAkxKc0aoF6Q7QMHS9Q1Li0eOMVzUSt4FzSbSsbvy1ajMI0L68c6G3ZU4F",
                content = reply
            )
            _isPeerTyping.value = false
        }
    }

    fun sendCodeSnippet(fileName: String, code: String) {
        if (code.isBlank()) return
        viewModelScope.launch {
            repository.sendCodeSnippet("conv_marcus", fileName, code, "Ananya")
            _toastEvent.emit("Code snippet attached to stream!")

            // Peer review reply
            _isPeerTyping.value = true
            kotlinx.coroutines.delay(1500)
            repository.sendPeerReply(
                convId = "conv_marcus",
                senderId = "user_marcus",
                senderName = "Marcus Vance",
                senderPhotoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAxeIA5BwEbpWePsYcT0c1tDLWTP62Kj4nFuEGdIBnuj97H4taihhPTH_D5sxBA4CNJ_DtujgzF-dw3zffOwJqqPxXnkcgyifHdNWl1f7dtp7l39t3zQAKFKaBdJC3vaX8qdZhwax8gtjqvO-rjpPwM9D-w7waKGL_bcVgsaAF7qCjAvglhaMQ047E7qknAkxKc0aoF6Q7QMHS9Q1Li0eOMVzUSt4FzSbSsbvy1ajMI0L68c6G3ZU4F",
                content = "Great code snippet in `$fileName`! The ownership flow is clear. On line 3, using `&mut` prevents heap allocation overhead. Let's run benchmarks on it in our live room!"
            )
            _isPeerTyping.value = false
        }
    }

    fun addSkill(title: String, category: String, type: String, proficiency: String, description: String) {
        viewModelScope.launch {
            repository.addSkill("user_ananya", title, category, type, proficiency, description)
            _isAddSkillDialogOpen.value = false
            _toastEvent.emit("Skill '$title' successfully listed!")
        }
    }

    fun deleteSkill(id: String) {
        viewModelScope.launch {
            repository.deleteSkill(id)
            _toastEvent.emit("Skill removed from your profile.")
        }
    }

    fun updateProfilePhoto(photoUrl: String) {
        viewModelScope.launch {
            val current = currentUser.value ?: defaultUser
            val updated = current.copy(photoUrl = photoUrl)
            repository.updateUser(updated)
            _isAvatarPickerOpen.value = false
            _toastEvent.emit("Profile picture updated successfully!")
        }
    }

    fun updateProfile(
        name: String,
        college: String,
        branch: String,
        semester: String,
        bio: String,
        availability: String,
        photoUrl: String = ""
    ) {
        viewModelScope.launch {
            val current = currentUser.value ?: defaultUser
            val updated = current.copy(
                name = name,
                college = college,
                branch = branch,
                semester = semester,
                bio = bio,
                availability = availability,
                photoUrl = if (photoUrl.isNotBlank()) photoUrl else current.photoUrl
            )
            repository.updateUser(updated)
            _isEditProfileOpen.value = false
            _isAvatarPickerOpen.value = false
            _toastEvent.emit("Profile updated successfully!")
        }
    }

    fun scheduleSession(topic: String, partnerName: String, date: String, time: String, durationMinutes: Int) {
        viewModelScope.launch {
            repository.scheduleSession(topic, partnerName, date, time, durationMinutes)
            _isScheduleSessionOpen.value = false
            _toastEvent.emit("Session '$topic' confirmed!")
        }
    }

    fun syncCalendar() {
        viewModelScope.launch {
            _toastEvent.emit("Calendar event synced with Google Calendar!")
        }
    }
}
