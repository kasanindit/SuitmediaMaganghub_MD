package com.example.suitmediamaganghub_md.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.suitmediamaganghub_md.data.model.DataItem
//import coil3.compose.AsyncImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserItem(
    user: DataItem,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = user.avatar,
            contentDescription = "Profile picture of ${user.firstName}",
            modifier = Modifier.size(52.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )

//        AsyncImage(
//            model = user.avatar,
//            contentDescription = "Profile picture of ${user.firstName}",
//            modifier = Modifier
//                .size(52.dp)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = user.email ?: "Email not available",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}