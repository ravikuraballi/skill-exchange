package com.example.ui.screens

import com.example.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapCalls
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.entity.LearningSessionEntity
import com.example.data.local.entity.UserEntity
import com.example.viewmodel.AppTab
import com.example.viewmodel.SkillSwapViewModel

@Composable
fun HomeScreen(
    viewModel: SkillSwapViewModel,
    currentUser: UserEntity?,
    upcomingSession: LearningSessionEntity?,
    onNavigateTab: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Welcome & Ambient Status Header
            WelcomeCard(
                user = currentUser,
                onEditClick = { viewModel.openEditProfile(true) }
            )
        }

        item {
            // Hero Spotlight: Reciprocal Match (Liam Chen)
            TopReciprocalMatchHero(
                onProposeSwap = {
                    val liam = UserEntity(
                        id = "user_liam",
                        name = "Liam Chen",
                        email = "liam.chen@statetech.edu",
                        college = "State Tech '25",
                        branch = "CS & Systems",
                        semester = "CS 8th Sem",
                        bio = "Senior backend engineer specializing in high-throughput microservices.",
                        photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCB8797vpI7_IYD8bITgD9JR3ZWy5KdRRkoILo6cA7eNHdZrmjaZmAMpfUhWYMagN_jI9bW1Hiwbbd1D98ZMR73hUSelTqJBpeFJjAE2lRZ1nBToipYY6wJ30hms6xIShmPxFGcbIILoG580xxRDeCVmhFkWozlFtmWf0po4U9TDcdSRd7abt3IOQR3HqofpQI0XUgp4Jmu6bjql7_vuYjTPSCjZrpTl1cWZwh5y5y41dt4yyUUHfQH",
                        offersSummary = "System Design, Microservices",
                        wantsSummary = "React & TypeScript",
                        matchPercentage = 96
                    )
                    viewModel.openProposeSwap(liam)
                }
            )
        }

        item {
            // Stats Ribbon
            StatsRibbon(user = currentUser)
        }

        item {
            // Upcoming Session Reminder
            UpcomingSessionCard(
                session = upcomingSession,
                onJoinCall = { viewModel.openVideoCall(true) },
                onDetailsClick = { viewModel.selectTab(AppTab.REQUESTS) }
            )
        }

        item {
            // AI Skill Match Recommendation (Maya Lin)
            AiMatchCard(
                onConnect = {
                    val maya = UserEntity(
                        id = "user_maya",
                        name = "Maya Lin",
                        email = "maya.lin@statetech.edu",
                        college = "State Tech University",
                        branch = "Design & HCI",
                        semester = "4th Sem",
                        bio = "Hackathon enthusiast! Teaching Figma heuristics.",
                        photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH",
                        offersSummary = "Figma Design Systems",
                        wantsSummary = "Python Scripting",
                        matchPercentage = 92
                    )
                    viewModel.openProposeSwap(maya)
                },
                onChat = { viewModel.selectTab(AppTab.CHAT) }
            )
        }

        item {
            // Quick Actions Grid
            QuickActionsGrid(
                onDiscover = { onNavigateTab(AppTab.DISCOVER) },
                onProposeSwap = { onNavigateTab(AppTab.DISCOVER) },
                onAddSkill = { viewModel.openAddSkillDialog(true) }
            )
        }

        item {
            // Campus Hot Skills & Live Feed
            TrendingCampusCard()
        }

        item {
            // Community Pods
            CommunityPodCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun WelcomeCard(
    user: UserEntity?,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box {
                    AsyncImage(
                        model = user?.photoUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Hey, ${user?.name?.split(" ")?.firstOrNull() ?: "Ananya"}!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.primaryFixed)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CS • ${user?.semester ?: "6th Sem"}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryFixedVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${user?.college ?: "State Tech University"} • Karma ${user?.karma ?: 740}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun TopReciprocalMatchHero(
    onProposeSwap: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF3525CD),
                            Color(0xFF4F46E5),
                            Color(0xFF5C00CA)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                // Top Tag Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.18f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF82F5C1))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TOP RECIPROCAL MATCH",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF82F5C1)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF85F8C4),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "96% Fit",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Peer Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCB8797vpI7_IYD8bITgD9JR3ZWy5KdRRkoILo6cA7eNHdZrmjaZmAMpfUhWYMagN_jI9bW1Hiwbbd1D98ZMR73hUSelTqJBpeFJjAE2lRZ1nBToipYY6wJ30hms6xIShmPxFGcbIILoG580xxRDeCVmhFkWozlFtmWf0po4U9TDcdSRd7abt3IOQR3HqofpQI0XUgp4Jmu6bjql7_vuYjTPSCjZrpTl1cWZwh5y5y41dt4yyUUHfQH",
                        contentDescription = "Liam Chen",
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Liam Chen",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "State Tech '25",
                                color = Color(0xFFDAD7FF),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Can teach you System Design in direct exchange for your React & TypeScript skills!",
                            color = Color(0xFFDAD7FF),
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Swap Bridge Chip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF006C4A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "You learn: System Design",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.SyncAlt,
                            contentDescription = null,
                            tint = Color(0xFFDAD7FF),
                            modifier = Modifier.size(16.dp)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE2DFFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    tint = Color(0xFF3323CC),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "You teach: React Native",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onProposeSwap,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("hero_propose_swap_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF85F8C4),
                            contentColor = Color(0xFF002114)
                        ),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ElectricBolt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Propose Immediate Swap", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { /* Save to bookmarks */ },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                    ) {
                        Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = "Bookmark", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsRibbon(user: UserEntity?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(value = "${user?.completedExchanges ?: 4}", label = "Completed", color = MaterialTheme.colorScheme.primary)
            StatItem(value = "${user?.hoursExchanged ?: 18}h", label = "Exchanged", color = MaterialTheme.colorScheme.secondary, isPill = true)
            StatItem(value = "${user?.reputation ?: 4.9}", label = "Reputation", color = MaterialTheme.colorScheme.onSurface, hasStar = true)
            StatItem(value = "2", label = "Pending", color = MaterialTheme.colorScheme.tertiary, isTertiaryPill = true)
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    color: Color,
    hasStar: Boolean = false,
    isPill: Boolean = false,
    isTertiaryPill: Boolean = false
) {
    val bgModifier = when {
        isPill -> Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 12.dp, vertical = 6.dp)
        isTertiaryPill -> Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.tertiaryFixed.copy(alpha = 0.4f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        else -> Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
    }

    Column(
        modifier = bgModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
            if (hasStar) {
                Spacer(modifier = Modifier.width(2.dp))
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(14.dp))
            }
        }
        Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun UpcomingSessionCard(
    session: LearningSessionEntity?,
    onJoinCall: () -> Unit,
    onDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Upcoming Swap Session",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Confirmed",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time & Lab Box
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "TOMORROW", fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                            Text(text = session?.time?.take(4) ?: "4:00", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(text = "PM", fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = session?.topic ?: "Docker & Kubernetes Lab",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "with ${session?.partnerName ?: "David Kim"} • 60 min session",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onJoinCall,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("join_virtual_study_room_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Join Virtual Study Room", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onDetailsClick,
                    modifier = Modifier.height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Details", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun AiMatchCard(
    onConnect: () -> Unit,
    onChat: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "SkillSwap AI Match", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = "Optimized for mutual skill gains", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryFixed)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = "92% Match", color = MaterialTheme.colorScheme.onPrimaryFixedVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Maya Lin Profile snippet
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH",
                                contentDescription = "Maya Lin",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "Maya Lin", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "Design & HCI • 4th Sem", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(10.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(text = "3 hrs/wk", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Teaches / Learns Rows
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "TEACHES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.width(60.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            SkillChip("Figma Design Systems", isSuccess = true)
                            SkillChip("UX Heuristics", isSuccess = true)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "LEARNS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.width(60.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            SkillChip("Python Scripting", isTertiary = true)
                            SkillChip("Data Scraping", isTertiary = true)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // AI rationale pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Mutual syllabus overlap: You both registered for the Hackathon Prep Track next month and have complementary free slots on Thursday evenings.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onConnect,
                    modifier = Modifier.weight(1f).height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Handshake, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Connect with Maya", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                IconButton(
                    onClick = onChat,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Icon(imageVector = Icons.Default.ChatBubbleOutline, contentDescription = "Chat", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun SkillChip(text: String, isSuccess: Boolean = false, isTertiary: Boolean = false) {
    val bg = when {
        isSuccess -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        isTertiary -> MaterialTheme.colorScheme.tertiaryFixed.copy(alpha = 0.6f)
        else -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = when {
        isSuccess -> MaterialTheme.colorScheme.onSecondaryContainer
        isTertiary -> MaterialTheme.colorScheme.onTertiaryFixedVariant
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = text, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = textColor)
    }
}

@Composable
private fun QuickActionsGrid(
    onDiscover: () -> Unit,
    onProposeSwap: () -> Unit,
    onAddSkill: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Quick Actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = "View All",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onDiscover() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionTile(
                title = "Discover Peers",
                subtitle = "Find campus mentors",
                icon = Icons.Default.GroupAdd,
                iconBg = MaterialTheme.colorScheme.primaryFixed,
                iconColor = MaterialTheme.colorScheme.primary,
                onClick = onDiscover,
                modifier = Modifier.weight(1f)
            )

            ActionTile(
                title = "Propose Swap",
                subtitle = "Create 1-on-1 offer",
                icon = Icons.Default.SwapCalls,
                iconBg = MaterialTheme.colorScheme.secondaryFixed,
                iconColor = MaterialTheme.colorScheme.secondary,
                onClick = onProposeSwap,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionTile(
                title = "List Skill",
                subtitle = "Teach or learn new topic",
                icon = Icons.Default.AutoAwesome,
                iconBg = MaterialTheme.colorScheme.tertiaryFixed,
                iconColor = MaterialTheme.colorScheme.tertiary,
                onClick = onAddSkill,
                modifier = Modifier.weight(1f)
            )

            ActionTile(
                title = "Practice Quiz",
                subtitle = "Validate peer badges",
                icon = Icons.Default.Quiz,
                iconBg = MaterialTheme.colorScheme.surfaceVariant,
                iconColor = MaterialTheme.colorScheme.onSurface,
                onClick = { /* Quiz demo */ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ActionTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TrendingCampusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Trending on Campus", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
                Text(text = "Live Feed", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Trending Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "Prompt Eng. (+34%)", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "Linear Algebra", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "Rust Systems", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Micro feed list
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryFixed, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Rohan swapped SQL Optimization with Sara",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "12 mins ago • Engineering Wing", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.MilitaryTech, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Elena K. earned Level 3 Peer Tutor in UI/UX",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "45 mins ago • State Tech Design Guild", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunityPodCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuB0ctgwkd-y2YXWrgD1sA8pWMB4I5XgFzCq3oUmYZjWW_dUwSr0Az0v63nl_Xqrg8Bb-M602hKb3tvcSAeBbKYjFdLY5qtojl529FR39gHf9-hOfC4otu90h9Q9gyCQXBYD25cU_P6oeHMC2LYq7w2HYJ2x6EVlIwy-C67umb4WAAsOVHf-guf5Quf_x5lqcmceiLmgG30HNcsLfwjuuK7malNzjUw1uuu6NK-8dcFpXhhQkymRjsEm",
                contentDescription = "Study Pods",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = "CAMPUS COMMUNITY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Study Pods Open for Finals", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Join CS301 Algorithms peer circle with 14 other peers.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
