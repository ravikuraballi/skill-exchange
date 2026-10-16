package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import com.example.data.local.entity.UserEntity

data class AvatarOption(
    val name: String,
    val major: String,
    val url: String
)

val campusAvatarList = listOf(
    AvatarOption(
        "Ananya",
        "CS & Systems",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuA9cdKNYpSu6bFO5Ndgzn6dG3evLMtubNmKPb1mbNe-qvOEBQgcYN_b1xkY6g6aF7H8uS0tbA2JH_Q2lnWWSnzahGw30Sa2QpcFD07-7NMYR7mON2Kmma0ufTbPLUzLPmrTAcjhfWCxBdxagb-8y7kWj3jPooMlvQD0kp0YwuRrWSUePfWj66S0bCiZbxvk7On_I4zupB-zOTWU_OVeybMl2F75YIJ7V0XjybvTK7-GepBxwqXIIC96"
    ),
    AvatarOption(
        "Marcus",
        "EE & Rust",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuAxeIA5BwEbpWePsYcT0c1tDLWTP62Kj4nFuEGdIBnuj97H4taihhPTH_D5sxBA4CNJ_DtujgzF-dw3zffOwJqqPxXnkcgyifHdNWl1f7dtp7l39t3zQAKFKaBdJC3vaX8qdZhwax8gtjqvO-rjpPwM9D-w7waKGL_bcVgsaAF7qCjAvglhaMQ047E7qknAkxKc0aoF6Q7QMHS9Q1Li0eOMVzUSt4FzSbSsbvy1ajMI0L68c6G3ZU4F"
    ),
    AvatarOption(
        "Priya",
        "UI/UX Design",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuBkktR2RXqlIjA1xEw3aGmU9KdU_2cmEnT7WmQTKqN4RJ-G5Nju4scxJBwJ0CmcHetNxjPeZDpoP4MchiswDWcG-TarAoe57kHqABWbiig4WmUrFD1Y4XRG6THx4PDhSyYfciZELbw72MZxDVbE5pTkQhYUvJrXfnr98ew-zUvd41k6eYWDnr_M0_9N9nUMhFztbvGM9QEz9qG_776c9dsWkL1sspbaQn_eoylZJAk0B7kQPfi77M6h"
    ),
    AvatarOption(
        "Elena",
        "Math & Algo",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDgfiZwlQRfthGpI8ECNEsjgNx-Wn4RET440mhJ2DoztyTjo3cFJZq3pekrJXgCjMntwDRn2Xflog9pgD-i-1LLlUMVBUpougEtikik5OJLI_UqIfVeqc0tUtB7R6dI861Ckw2eDLM2j8FLVZ2kqrvMy8KY2yhP1H-Uaqds2hQJsD4fhAQPbCFJ8FpalcF5-0Yk7KB6ywRy-S9fiy_O4Tf8qq-oL-EBiO3ifiotecEjZlAS96cfMnsJ"
    ),
    AvatarOption(
        "Liam",
        "Cloud & Systems",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCB8797vpI7_IYD8bITgD9JR3ZWy5KdRRkoILo6cA7eNHdZrmjaZmAMpfUhWYMagN_jI9bW1Hiwbbd1D98ZMR73hUSelTqJBpeFJjAE2lRZ1nBToipYY6wJ30hms6xIShmPxFGcbIILoG580xxRDeCVmhFkWozlFtmWf0po4U9TDcdSRd7abt3IOQR3HqofpQI0XUgp4Jmu6bjql7_vuYjTPSCjZrpTl1cWZwh5y5y41dt4yyUUHfQH"
    ),
    AvatarOption(
        "Maya",
        "Design Systems",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuBbTLzmJMZEULhysL4tB1B9P69a6aHhHCp1R2t4PFSN4J5rHwEhZvE8ldWlYNXZb-gSpDfhW6gtPA2x6Dc2Cgvaa9Tny3JLbfwjo1GYZo1CD4XiLjuLQgfuvnGUwH5T5gFrN6GYZnUQFddCpFYpZ_ayIB4wuz2vMhGG1X7p_drC46EkQGeFJ2Mq2-2gVzDEacA_-629ZvxiDRDI-QB2g1_epXBx2YnCSeZWmBsgQuLA3CIvNA938ceH"
    ),
    AvatarOption(
        "Alex",
        "Backend & DevOps",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDmTvVltswXSQxUdo-OFo9EV_9XNwfBx6RehKvurtf1kt5SbmzN5aXeXMnH3TVCSI8NakQEGHdReDkXi7HFcvYDtdMjDwgUdXiZ8piNnQQpFhLSBtzaiZhiyLhhZwBS9PqbdiSnx4ZwTLxlYCC3sB_OHgZHsjQe0w6wrapslXIcIkalzpmcOIXmRjho7-IIZXDl0zvDXYfM_HnpdM48HCBmQ2_Qx9piuvSEKoHJUIw2dlv72p0lih5"
    ),
    AvatarOption(
        "Sophia",
        "Data & ML",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuB0_OzBAnl5vR-Tl_G3Pz66H6bNmlkjuzW_XtbmUWVZvS-M652gKg5pmO4AUoaZa8pAJpumsWjbtDoHJxsPFGp0PoztTSsMDE3h9nDAguK4BzaK81MWWw_4HB1IQ64eml8F5TFaimvBELo5zb8wkRCWbPhlPmze_-9L67HYGEJSmyxwUYbdMQnG2KSlN_sZFxuCngAmuUNE3KWzRnoFpjw7pKFlQfl1f1pVKMX4g4HTnoHsVepRKQyj"
    ),
    AvatarOption(
        "David",
        "DevOps & K8s",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDoaKCbrPe81v0rw1KXT_sttPkZrfDFPGFAvRkzsM6QPO-ImDp-W1DKuns_-TNurdBA-ZQ8iPFhJSMAI4oLGYsLJmtq04qcYphyYAr6kvoqvG5h2drWIc_DFZhvQItmbAjcmzTyTXmBlx8PVwRplt1Ll0s4iFTi2WsJWzOXVPqHm416CapZp4XVu-YMkGcC2KViK1_hv5RL2tqGWC3chHGknoz017gK-4ntQyTsfqvXAObm8JJTOBIN"
    ),
    AvatarOption(
        "Chloe",
        "Cybersecurity",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuClGyQAMlsvVEO4Rs6qZdVQKwB31AAsyOcvJUcDmmqB0W-W6pdjuCS4gVhF477d95WuaxfP2QiELZHzs-xkUxrq73CtF-3rP72zQ-X7Sg_qzeZvALcRzS1zxOgjZznVdrFexl89wFjhrjrGXTJWXkrVgMcw904hqLeAS3_N-WN_KvMks9lRhJfv6RoOWOPcRRk6-JMdNQugBtukzCEk_kQOceLE50kmhKO6a4y3LTxZOVxMbanppIjx"
    )
)

@Composable
fun AvatarPickerModal(
    currentPhotoUrl: String,
    onDismiss: () -> Unit,
    onPhotoSelected: (String) -> Unit
) {
    var previewUrl by remember { mutableStateOf(currentPhotoUrl) }
    var customUrlInput by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            previewUrl = uri.toString()
            onPhotoSelected(uri.toString())
        }
    }

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
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Change Profile Picture",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Choose an avatar or upload from device",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Large Live Preview Avatar
                Box(contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = previewUrl.ifEmpty { currentPhotoUrl },
                        contentDescription = "Selected Profile Picture",
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
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
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Select from Gallery Button
                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("choose_from_gallery_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Choose from Device Gallery",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Campus Avatars Grid Title
                Text(
                    text = "Select a Student Avatar (Tap to apply):",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 2-row / Grid of avatar options
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 160.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    items(campusAvatarList) { opt ->
                        val isSelected = previewUrl == opt.url
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    previewUrl = opt.url
                                    // Instantly update photo on tap!
                                    onPhotoSelected(opt.url)
                                }
                                .padding(2.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                AsyncImage(
                                    model = opt.url,
                                    contentDescription = opt.name,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .border(
                                            if (isSelected) 2.5.dp else 1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                                            CircleShape
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(15.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(10.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = opt.name,
                                fontSize = 9.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Custom URL input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = customUrlInput,
                        onValueChange = { customUrlInput = it },
                        placeholder = { Text("Paste image URL...", fontSize = 11.sp) },
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
                                val url = customUrlInput.trim()
                                previewUrl = url
                                onPhotoSelected(url)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(52.dp)
                    ) {
                        Text("Apply", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Confirm / Close Button
                Button(
                    onClick = {
                        onPhotoSelected(previewUrl)
                        onDismiss()
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
fun EditProfileDialog(
    user: UserEntity,
    onDismiss: () -> Unit,
    onChangePhotoClick: () -> Unit,
    onSave: (name: String, college: String, branch: String, semester: String, bio: String, availability: String, photoUrl: String) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var college by remember { mutableStateOf(user.college) }
    var branch by remember { mutableStateOf(user.branch) }
    var semester by remember { mutableStateOf(user.semester) }
    var bio by remember { mutableStateOf(user.bio) }
    var availability by remember { mutableStateOf(user.availability) }
    var photoUrl by remember { mutableStateOf(user.photoUrl) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Edit Student Profile",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onChangePhotoClick() }
                    ) {
                        AsyncImage(
                            model = photoUrl.ifEmpty { user.photoUrl },
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Profile Picture", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(text = "Tap to pick from 10+ avatars", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                        onClick = {
                            onSave(name, college, branch, semester, bio, availability, photoUrl)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}
