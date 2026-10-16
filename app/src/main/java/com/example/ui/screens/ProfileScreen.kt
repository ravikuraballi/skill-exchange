package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.local.entity.SkillItemEntity
import com.example.data.local.entity.UserEntity
import com.example.viewmodel.SkillSwapViewModel

@Composable
fun ProfileScreen(
    viewModel: SkillSwapViewModel,
    currentUser: UserEntity?,
    skills: List<SkillItemEntity>,
    modifier: Modifier = Modifier
) {
    var isEditProfileOpen by remember { mutableStateOf(false) }
    var isAvatarPickerOpen by remember { mutableStateOf(false) }
    var publicProfileToggle by remember { mutableStateOf(true) }
    var sameCampusToggle by remember { mutableStateOf(false) }

    val teachingSkills = skills.filter { it.type == "TEACH" }
    val learningSkills = skills.filter { it.type == "LEARN" }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Student Hero Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Tappable Profile Photo with Edit Badge
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { isAvatarPickerOpen = true }
                                .testTag("edit_avatar_button")
                        ) {
                            AsyncImage(
                                model = currentUser?.photoUrl,
                                contentDescription = "Profile Photo",
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(CircleShape)
                                    .border(2.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = "Edit Profile Photo",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Quick "Change Photo" action pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryFixed.copy(alpha = 0.5f))
                                .clickable { isAvatarPickerOpen = true }
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Change Photo",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentUser?.name ?: "Ananya Sharma",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Student",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Text(
                            text = currentUser?.email ?: "ananya.sharma@statetech.edu",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${currentUser?.college ?: "State Tech University"} • ${currentUser?.branch ?: "Computer Science"}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Bio
                        Text(
                            text = currentUser?.bio ?: "Building high-performance mobile apps & distributed backend systems.",
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { isEditProfileOpen = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.height(38.dp).testTag("edit_profile_button")
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Edit Student Profile", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Skills I Offer Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Skills I Teach (${teachingSkills.size})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }

                    TextButton(onClick = { viewModel.openAddSkillDialog(true) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Skill")
                    }
                }
            }

            items(teachingSkills, key = { it.id }) { skill ->
                SkillListItem(
                    skill = skill,
                    isTeaching = true,
                    onDelete = { viewModel.deleteSkill(skill.id) }
                )
            }

            // Skills I Want to Learn Section
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Skills I Want to Learn (${learningSkills.size})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }

                    TextButton(onClick = { viewModel.openAddSkillDialog(true) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Target")
                    }
                }
            }

            items(learningSkills, key = { it.id }) { skill ->
                SkillListItem(
                    skill = skill,
                    isTeaching = false,
                    onDelete = { viewModel.deleteSkill(skill.id) }
                )
            }

            // Privacy & Preferences Settings
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Privacy & Campus Settings", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Public SkillSwap Profile", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(text = "Allow peers on campus to find your skills", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = publicProfileToggle, onCheckedChange = { publicProfileToggle = it })
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Campus Exclusive Matching", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(text = "Only match with students in State Tech University", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = sameCampusToggle, onCheckedChange = { sameCampusToggle = it })
                        }
                    }
                }
            }

            // Logout / Sign Out
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = { /* Demo account status */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Sign Out", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Dedicated Avatar / Profile Picture Picker Dialog
        if (isAvatarPickerOpen && currentUser != null) {
            AvatarPickerModal(
                currentPhotoUrl = currentUser.photoUrl,
                onDismiss = { isAvatarPickerOpen = false },
                onPhotoSelected = { newPhotoUrl ->
                    viewModel.updateProfilePhoto(newPhotoUrl)
                    isAvatarPickerOpen = false
                }
            )
        }

        // Edit Profile Dialog
        if (isEditProfileOpen && currentUser != null) {
            EditProfileDialog(
                user = currentUser,
                onDismiss = { isEditProfileOpen = false },
                onChangePhotoClick = {
                    isEditProfileOpen = false
                    isAvatarPickerOpen = true
                },
                onSave = { name, college, branch, semester, bio, availability ->
                    viewModel.updateProfile(name, college, branch, semester, bio, availability)
                    isEditProfileOpen = false
                }
            )
        }
    }
}

@Composable
fun AvatarPickerModal(
    currentPhotoUrl: String,
    onDismiss: () -> Unit,
    onPhotoSelected: (String) -> Unit
) {
    var previewUrl by remember { mutableStateOf(currentPhotoUrl) }
    var customUrlInput by remember { mutableStateOf("") }

    // Android Zero-permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            previewUrl = uri.toString()
            onPhotoSelected(uri.toString())
        }
    }

    // Curated high quality campus avatar presets
    val presetAvatars = listOf(
        Pair("Ananya (CS)", "https://lh3.googleusercontent.com/aida-public/AB6AXuA9cdKNYpSu6bFO5Ndgzn6dG3evLMtubNmKPb1mbNe-qvOEBQgcYN_b1xkY6g6aF7H8uS0tbA2JH_Q2lnWWSnzahGw30Sa2QpcFD07-7NMYR7mON2Kmma0ufTbPLUzLPmrTAcjhfWCxBdxagb-8y7kWj3jPooMlvQD0kp0YwuRrWSUePfWj66S0bCiZbxvk7On_I4zupB-zOTWU_OVeybMl2F75YIJ7V0XjybvTK7-GepBxwqXIIC96"),
        Pair("Marcus (EE)", "https://lh3.googleusercontent.com/aida-public/AB6AXuAxeIA5BwEbpWePsYcT0c1tDLWTP62Kj4nFuEGdIBnuj97H4taihhPTH_D5sxBA4CNJ_DtujgzF-dw3zffOwJqqPxXnkcgyifHdNWl1f7dtp7l39t3zQAKFKaBdJC3vaX8qdZhwax8gtjqvO-rjpPwM9D-w7waKGL_bcVgsaAF7qCjAvglhaMQ047E7qknAkxKc0aoF6Q7QMHS9Q1Li0eOMVzUSt4FzSbSsbvy1ajMI0L68c6G3ZU4F"),
        Pair("Priya (Design)", "https://lh3.googleusercontent.com/aida-public/AB6AXuBkktR2RXqlIjA1xEw3aGmU9KdU_2cmEnT7WmQTKqN4RJ-G5Nju4scxJBwJ0CmcHetNxjPeZDpoP4MchiswDWcG-TarAoe57kHqABWbiig4WmUrFD1Y4XRG6THx4PDhSyYfciZELbw72MZxDVbE5pTkQhYUvJrXfnr98ew-zUvd41k6eYWDnr_M0_9N9nUMhFztbvGM9QEz9qG_776c9dsWkL1sspbaQn_eoylZJAk0B7kQPfi77M6h"),
        Pair("Elena (Math)", "https://lh3.googleusercontent.com/aida-public/AB6AXuDgfiZwlQRfthGpI8ECNEsjgNx-Wn4RET440mhJ2DoztyTjo3cFJZq3pekrJXgCjMntwDRn2Xflog9pgD-i-1LLlUMVBUpougEtikik5OJLI_UqIfVeqc0tUtB7R6dI861Ckw2eDLM2j8FLVZ2kqrvMy8KY2yhP1H-Uaqds2hQJsD4fhAQPbCFJ8FpalcF5-0Yk7KB6ywRy-S9fiy_O4Tf8qq-oL-EBiO3ifiotecEjZlAS96cfMnsJ"),
        Pair("Liam (Systems)", "https://lh3.googleusercontent.com/aida-public/AB6AXuCB8797vpI7_IYD8bITgD9JR3ZWy5KdRRkoILo6cA7eNHdZrmjaZmAMpfUhWYMagN_jI9bW1Hiwbbd1D98ZMR73hUSelTqJBpeFJjAE2lRZ1nBToipYY6wJ30hms6xIShmPxFGcbIILoG580xxRDeCVmhFkWozlFtmWf0po4U9TDcdSRd7abt3IOQR3HqofpQI0XUgp4Jmu6bjql7_vuYjTPSCjZrpTl1cWZwh5y5y41dt4yyUUHfQH"),
        Pair("Maya (HCI)", "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH"),
        Pair("Alex (Backend)", "https://lh3.googleusercontent.com/aida-public/AB6AXuDmTvVltswXSQxUdo-OFo9EV_9XNwfBx6RehKvurtf1kt5SbmzN5aXeXMnH3TVCSI8NakQEGHdReDkXi7HFcvYDtdMjDwgUdXiZ8piNnQQpFhLSBtzaiZhiyLhhZwBS9PqbdiSnx4ZwTLxlYCC3sB_OHgZHsjQe0w6wrapslXIcIkalzpmcOIXmRjho7-IIZXDl0zvDXYfM_HnpdM48HCBmQ2_Qx9piuvSEKoHJUIw2dlv72p0lih5"),
        Pair("Sophia (Data)", "https://lh3.googleusercontent.com/aida-public/AB6AXuB0_OzBAnl5vR-Tl_G3Pz66H6bNmlkjuzW_XtbmUWVZvS-M652gKg5pmO4AUoaZa8pAJpumsWjbtDoHJxsPFGp0PoztTSsMDE3h9nDAguK4BzaK81MWWw_4HB1IQ64eml8F5TFaimvBELo5zb8wkRCWbPhlPmze_-9L67HYGEJSmyxwUYbdMQnG2KSlN_sZFxuCngAmuUNE3KWzRnoFpjw7pKFlQfl1f1pVKMX4g4HTnoHsVepRKQyj")
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit Profile Picture",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Live Preview Avatar
                Box(contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = previewUrl,
                        contentDescription = "New Profile Picture Preview",
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Choose from Device Photo Picker
                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("choose_from_gallery_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Choose from Device Gallery", color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Student Avatars Carousel
                Text(
                    text = "Or Choose a Campus Avatar:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(presetAvatars) { (label, url) ->
                        val isSelected = previewUrl == url
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { previewUrl = url }
                                .padding(4.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = label,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .border(
                                            if (isSelected) 2.5.dp else 1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                                            CircleShape
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Custom URL Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = customUrlInput,
                        onValueChange = { customUrlInput = it },
                        placeholder = { Text("Paste custom image URL...", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Link, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (customUrlInput.isNotBlank()) {
                                previewUrl = customUrlInput.trim()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(52.dp)
                    ) {
                        Text("Apply", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Confirm Save
                Button(
                    onClick = {
                        onPhotoSelected(previewUrl)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("save_profile_picture_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Save Profile Picture", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SkillListItem(
    skill: SkillItemEntity,
    isTeaching: Boolean,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = skill.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isTeaching) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryFixed
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = skill.proficiency,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isTeaching) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onTertiaryFixedVariant
                        )
                    }
                }
                if (skill.description.isNotEmpty()) {
                    Text(text = skill.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun EditProfileDialog(
    user: UserEntity,
    onDismiss: () -> Unit,
    onChangePhotoClick: () -> Unit,
    onSave: (name: String, college: String, branch: String, semester: String, bio: String, availability: String) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var college by remember { mutableStateOf(user.college) }
    var branch by remember { mutableStateOf(user.branch) }
    var semester by remember { mutableStateOf(user.semester) }
    var bio by remember { mutableStateOf(user.bio) }
    var availability by remember { mutableStateOf(user.availability) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Edit Student Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                // Avatar preview with Change button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = user.photoUrl,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Profile Picture", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(text = "Tap to update avatar", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    TextButton(onClick = onChangePhotoClick) {
                        Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Change", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Student Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = college,
                    onValueChange = { college = it },
                    label = { Text("College / University") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = branch,
                        onValueChange = { branch = it },
                        label = { Text("Branch / Major") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = semester,
                        onValueChange = { semester = it },
                        label = { Text("Semester / Year") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = availability,
                    onValueChange = { availability = it },
                    label = { Text("Availability (e.g. Weekday evenings)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Short Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onSave(name, college, branch, semester, bio, availability) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}
