package com.sobolev.beerratings.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sobolev.beerratings.R
import com.sobolev.beerratings.domain.model.Region
import com.sobolev.beerratings.domain.model.RegionToDisplay
import com.sobolev.beerratings.ui.theme.LightGray
import com.sobolev.beerratings.ui.theme.MainTheme

val regions = listOf(
    RegionToDisplay(Region.BELARUS, R.drawable.ic_belarus_flag, true),
    RegionToDisplay(Region.POLAND, R.drawable.ic_poland_flag, true),
    RegionToDisplay(Region.UKRAINE, R.drawable.ic_unkraine_flag, true),
    RegionToDisplay(Region.KAZAKHSTAN, R.drawable.ic_belarus_flag, true),
    RegionToDisplay(Region.GERMANY, R.drawable.ic_germany_flag, true),
    RegionToDisplay(Region.CHINA, R.drawable.ic_belarus_flag, true),
    RegionToDisplay(Region.LATVIA, R.drawable.ic_belarus_flag, true),
)

@Composable
fun RegionScreen(
    regionToDisplays: List<RegionToDisplay> = regions,
    onItemClicked: (regionToDisplay: RegionToDisplay) -> Unit
) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Choose your region", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We’ll show the most relevant reviews for your country",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 60.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp) // TODO: ?Top/Bottom list paddings
            ) {
                items(regionToDisplays.filter { it.available }) { region ->
                    // TODO: Show region availability
                    RegionItem(regionToDisplay = region, onItemClicked)
                }
            }
        }
    }
}

@Composable
fun RegionItem(
    regionToDisplay: RegionToDisplay,
    onItemClicked: (regionToDisplay: RegionToDisplay) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onItemClicked(regionToDisplay)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray)
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = regionToDisplay.iconDrawable),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(
                        CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = regionToDisplay.region.toString(),
                modifier = Modifier
                    .fillMaxHeight(),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegion() {
    MainTheme {
        RegionScreen(
            onItemClicked = { clicked ->
                Log.d("REGION", "On Region Clicked: ${clicked.region}")
            }
        )
    }
}