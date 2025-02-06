package com.example.swipeassignment.presentation.ui.screens.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.swipeassignment.R
import com.example.swipeassignment.data.model.product_list_response.ProductListResponseDTO
import com.example.swipeassignment.presentation.theme.Spacing
import com.example.swipeassignment.presentation.util.AppNetworkImage

@Composable
fun ProductItem(
    product: ProductListResponseDTO,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.medium)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.small)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {

            AppNetworkImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(RoundedCornerShape(Spacing.medium)),
                model = product.image,
                errorImage = painterResource(id = R.drawable.no_image)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = Spacing.medium),
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    text = product.product_name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = Spacing.small),
                    text = "Type - ${product.product_type}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Price - ₹${product.price}, Tax - ₹${product.tax}",
                    style = MaterialTheme.typography.titleSmall
                )

            }
        }

    }

}