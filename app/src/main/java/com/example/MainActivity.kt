package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.SkillSwapDatabase
import com.example.data.repository.SkillSwapRepository
import com.example.ui.components.AddSkillDialog
import com.example.ui.components.AiDoubtSolverBottomSheet
import com.example.ui.components.BottomNavBar
import com.example.ui.components.NotificationDrawer
import com.example.ui.components.ProposeSwapDialog
import com.example.ui.components.SessionCallDialog
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RequestsScreen
import com.example.ui.theme.SkillSwapTheme
import com.example.viewmodel.AppTab
import com.example.viewmodel.SkillSwapViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy {
        SkillSwapDatabase.getDatabase(applicationContext)
    }

    private val repository by lazy {
        SkillSwapRepository(database.skillSwapDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SkillSwapViewModel(repository) as T
            }
        }

        setContent {
            SkillSwapTheme {
                val vm: SkillSwapViewModel = viewModel(factory = viewModelFactory)
                SkillSwapApp(viewModel = vm)
            }
        }
    }
}

@Composable
fun SkillSwapApp(viewModel: SkillSwapViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val peers by viewModel.filteredPeers.collectAsStateWithLifecycle()
    val incomingRequests by viewModel.incomingRequests.collectAsStateWithLifecycle()
    val sentRequests by viewModel.sentRequests.collectAsStateWithLifecycle()
    val historyRequests by viewModel.historyRequests.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotifCount by viewModel.unreadNotificationCount.collectAsStateWithLifecycle()
    val upcomingSession by viewModel.upcomingSession.collectAsStateWithLifecycle()
    val userSkills by viewModel.userSkills.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedFilterChip by viewModel.selectedFilterChip.collectAsStateWithLifecycle()
    val sortMode by viewModel.sortMode.collectAsStateWithLifecycle()

    val selectedPeerForProposal by viewModel.selectedPeerForProposal.collectAsStateWithLifecycle()
    val isFilterSheetOpen by viewModel.isFilterSheetOpen.collectAsStateWithLifecycle()
    val isNotificationDrawerOpen by viewModel.isNotificationDrawerOpen.collectAsStateWithLifecycle()
    val isAiDrawerOpen by viewModel.isAiDrawerOpen.collectAsStateWithLifecycle()
    val isVideoCallActive by viewModel.isVideoCallActive.collectAsStateWithLifecycle()
    val isAddSkillDialogOpen by viewModel.isAddSkillDialogOpen.collectAsStateWithLifecycle()

    // Transient Toast Banner message
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { msg ->
            toastMessage = msg
            delay(3500)
            if (toastMessage == msg) {
                toastMessage = null
            }
        }
    }

    // Handle Android system back gesture: return to Home if on another tab
    BackHandler(enabled = currentTab != AppTab.HOME) {
        viewModel.selectTab(AppTab.HOME)
    }

    val currentTitle = when (currentTab) {
        AppTab.HOME -> "Home"
        AppTab.DISCOVER -> "Discover"
        AppTab.REQUESTS -> "Requests"
        AppTab.CHAT -> "Chat"
        AppTab.PROFILE -> "Profile"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopHeaderBar(
                currentTitle = currentTitle,
                unreadNotificationCount = unreadNotifCount,
                userPhotoUrl = currentUser?.photoUrl ?: "",
                onNotificationsClick = { viewModel.openNotificationDrawer(true) },
                onProfileClick = { viewModel.selectTab(AppTab.PROFILE) },
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = currentTab,
                pendingRequestsCount = incomingRequests.size,
                unreadChatCount = 0,
                onTabSelected = { tab -> viewModel.selectTab(tab) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentTab) {
                AppTab.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        currentUser = currentUser,
                        upcomingSession = upcomingSession,
                        onNavigateTab = { tab -> viewModel.selectTab(tab) }
                    )
                }
                AppTab.DISCOVER -> {
                    DiscoverScreen(
                        viewModel = viewModel,
                        peers = peers,
                        searchQuery = searchQuery,
                        selectedFilterChip = selectedFilterChip,
                        sortMode = sortMode,
                        isFilterSheetOpen = isFilterSheetOpen
                    )
                }
                AppTab.REQUESTS -> {
                    RequestsScreen(
                        viewModel = viewModel,
                        incomingRequests = incomingRequests,
                        sentRequests = sentRequests,
                        historyRequests = historyRequests,
                        onOpenChat = { viewModel.selectTab(AppTab.CHAT) }
                    )
                }
                AppTab.CHAT -> {
                    ChatScreen(
                        viewModel = viewModel,
                        messages = chatMessages,
                        onLaunchCall = { viewModel.openVideoCall(true) },
                        onOpenAiDrawer = { viewModel.openAiDrawer(true) }
                    )
                }
                AppTab.PROFILE -> {
                    ProfileScreen(
                        viewModel = viewModel,
                        currentUser = currentUser,
                        skills = userSkills
                    )
                }
            }

            // Toast Floating Notification Banner
            AnimatedVisibility(
                visible = toastMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                val msg = toastMessage
                if (msg != null) {
                    androidx.compose.material3.Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .clip(RoundedCornerShape(14.dp)),
                        color = MaterialTheme.colorScheme.secondary,
                        shadowElevation = 6.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = msg,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Overlays

    // Propose Swap Dialog
    selectedPeerForProposal?.let { peer ->
        ProposeSwapDialog(
            peer = peer,
            onDismiss = { viewModel.openProposeSwap(null) },
            onSubmit = { offered, requested, note, slot ->
                viewModel.submitSwapProposal(peer, offered, requested, note, slot)
            }
        )
    }

    // AI Doubt Solver Drawer
    if (isAiDrawerOpen) {
        AiDoubtSolverBottomSheet(
            onDismiss = { viewModel.openAiDrawer(false) },
            onPasteToChat = { explanation ->
                viewModel.sendChatMessage(explanation)
                viewModel.openAiDrawer(false)
            }
        )
    }

    // Live Video Room Call Dialog
    if (isVideoCallActive) {
        SessionCallDialog(
            partnerName = if (currentTab == AppTab.CHAT) "Marcus Vance" else "David Kim",
            roomCode = "#SKILL-8024-LIVE",
            onEndCall = { viewModel.openVideoCall(false) }
        )
    }

    // Campus Notifications Drawer
    if (isNotificationDrawerOpen) {
        NotificationDrawer(
            notifications = notifications,
            onDismiss = { viewModel.openNotificationDrawer(false) },
            onMarkAllRead = { viewModel.openNotificationDrawer(false) }
        )
    }

    // Add Skill Dialog
    if (isAddSkillDialogOpen) {
        AddSkillDialog(
            onDismiss = { viewModel.openAddSkillDialog(false) },
            onSubmit = { title, category, type, proficiency, desc ->
                viewModel.addSkill(title, category, type, proficiency, desc)
            }
        )
    }
}
