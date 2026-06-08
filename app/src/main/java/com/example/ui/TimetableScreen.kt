package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.model.FestivalDay
import com.example.model.Performance
import com.example.model.PinkpopData
import com.example.ui.theme.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(
    viewModel: PinkpopViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val favoriteArtists by viewModel.favoriteArtists.collectAsState()
    val selectedDayIndex by viewModel.selectedDayIndex.collectAsState()
    val hiddenStages by viewModel.hiddenStages.collectAsState()

    // Screen pager state
    val pagerState = rememberPagerState(
        initialPage = 0, // Default Vrijdag
        pageCount = { PinkpopData.days.size }
    )

    // Sync from viewmodel index to pager state
    LaunchedEffect(selectedDayIndex) {
        if (pagerState.currentPage != selectedDayIndex) {
            pagerState.animateScrollToPage(selectedDayIndex)
        }
    }

    // Dialogue State for Artist Actions
    var selectedArtistForAction by remember { mutableStateOf<Pair<Performance, String>?>(null) } // Performance, StageName
    var showArtistActionDialog by remember { mutableStateOf(false) }

    // State for filtering "Only Favorites" on the timetable
    var showOnlyFavorites by remember { mutableStateOf(false) }

    // Collect all available stage names to render stage filters
    val allStageNames = remember {
        PinkpopData.days.flatMap { day -> day.stages.map { it.name } }.distinct()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PinkpopDarkBg,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinkpopWhite)
                    .statusBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                // Header bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PINKPOP 2026",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinkpopCardText,
                        letterSpacing = (-0.5).sp
                    )

                    // Compact "Only Favorites" Agenda toggle
                    IconButton(
                        onClick = { showOnlyFavorites = !showOnlyFavorites }
                    ) {
                        Icon(
                            imageVector = if (showOnlyFavorites) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorieten filter",
                            tint = if (showOnlyFavorites) PinkpopPink else PinkpopMuted
                        )
                    }
                }

                // Day Selection Tabs
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = PinkpopWhite,
                    contentColor = PinkpopPink,
                    divider = {
                        HorizontalDivider(color = PinkpopGridBg)
                    }
                ) {
                    PinkpopData.days.forEachIndexed { index, day ->
                        val isSelected = pagerState.currentPage == index
                        Tab(
                            selected = isSelected,
                            onClick = { viewModel.selectDay(index) },
                            text = {
                                Text(
                                    text = day.name,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (isSelected) PinkpopPink else PinkpopMuted
                                )
                            }
                        )
                    }
                }

                // Stage Filters Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Reset filters option
                    if (hiddenStages.isNotEmpty()) {
                        InputChip(
                            selected = false,
                            onClick = { viewModel.resetFilters() },
                            label = { Text("Toon Alles", fontSize = 11.sp, color = PinkpopPink, fontWeight = FontWeight.Bold) },
                            trailingIcon = { Icon(Icons.Default.Close, null, modifier = Modifier.size(12.dp), tint = PinkpopPink) },
                            colors = InputChipDefaults.inputChipColors(containerColor = PinkpopHighlightBg)
                        )
                    }

                    // Stages chips
                    allStageNames.forEach { stageName ->
                        val isVisible = !hiddenStages.contains(stageName)
                        FilterChip(
                            selected = isVisible,
                            onClick = { viewModel.toggleStageVisibility(stageName) },
                            label = {
                                Text(
                                    text = stageName.replace(" (FESTIVAL CAMPING)", "").replace(" (GROEPS CAMPING)", ""),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            leadingIcon = if (isVisible) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = PinkpopDarkBg,
                                selectedContainerColor = PinkpopHighlightBg,
                                selectedLabelColor = PinkpopHighlightText,
                                selectedLeadingIconColor = PinkpopPink,
                                labelColor = PinkpopMuted
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isVisible,
                                borderColor = if (isVisible) PinkpopPink else PinkpopGridBg,
                                borderWidth = 1.dp,
                                selectedBorderColor = PinkpopPink,
                                selectedBorderWidth = 1.dp
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val day = PinkpopData.days[page]
                TimetableGrid(
                    day = day,
                    favorites = favoriteArtists,
                    hiddenStages = hiddenStages,
                    showOnlyFavorites = showOnlyFavorites,
                    onArtistLongClick = { artist, stage ->
                        selectedArtistForAction = Pair(artist, stage)
                        showArtistActionDialog = true
                    }
                )
            }

            // Quick instruction helper overlays when favorites is empty
            if (showOnlyFavorites && favoriteArtists.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PinkpopWhite.copy(alpha = 0.95f))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.widthIn(max = 300.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Geen favorieten",
                            tint = PinkpopPink,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Geen favoriete bands",
                            color = PinkpopCardText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Houd het blokje van een band lang ingedrukt om deze als favoriet te markeren en notificaties in te stellen!",
                            color = PinkpopMuted,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Button(
                            onClick = { showOnlyFavorites = false },
                            colors = ButtonDefaults.buttonColors(containerColor = PinkpopPink)
                        ) {
                            Text("Toon Alle Bands", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Long press options dialog
    if (showArtistActionDialog && selectedArtistForAction != null) {
        val (artist, stageName) = selectedArtistForAction!!
        val isFavorite = favoriteArtists.contains(artist.artist)
        val day = PinkpopData.days[selectedDayIndex]

        Dialog(onDismissRequest = { showArtistActionDialog = false }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = PinkpopWhite,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PinkpopGridBg)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = artist.artist,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinkpopCardText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Podium: $stageName\nTijd: ${artist.startTime} - ${artist.endTime}",
                        fontSize = 14.sp,
                        color = PinkpopMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = PinkpopGridBg)

                    // Toggle Favorite Choice
                    Button(
                        onClick = {
                            viewModel.toggleFavorite(context, day, artist, stageName)
                            showArtistActionDialog = false
                            val msg = if (isFavorite) {
                                "${artist.artist} verwijderd uit favorieten."
                            } else {
                                "${artist.artist} toegevoegd aan favorieten. Notificatie ingesteld voor ${artist.startTime}!"
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFavorite) Color.Gray else PinkpopPink,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = if (isFavorite) "Verwijder van Favorieten" else "Markeer als Favoriet",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Open Link Choice (mits aanwezig)
                    if (!artist.url.isNullOrEmpty()) {
                        OutlinedButton(
                            onClick = {
                                showArtistActionDialog = false
                                try {
                                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(artist.url))
                                    context.startActivity(browserIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Kan link niet openen", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = PinkpopPink
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PinkpopPink),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = PinkpopPink,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Meer Informatie (Website)", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text(
                            text = "Geen website link beschikbaar voor deze artiest",
                            fontSize = 11.sp,
                            color = PinkpopMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Close Choice
                    TextButton(
                        onClick = { showArtistActionDialog = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Annuleren", color = PinkpopPink, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun TimetableGrid(
    day: FestivalDay,
    favorites: Set<String>,
    hiddenStages: Set<String>,
    showOnlyFavorites: Boolean,
    onArtistLongClick: (Performance, String) -> Unit
) {
    // We display hours from 11:00 AM to 04:00 AM next morning (represented as 11 to 28 hours)
    val gridStartHour = 11
    val gridEndHour = 28 // 04:00 AM next day
    val hoursList = (gridStartHour..gridEndHour).toList()
    
    val hourHeight = 90.dp
    val totalGridHeight = hourHeight * hoursList.size

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    // Determine which stages for this day are visible
    val visibleStages = day.stages.filter { stage ->
        !hiddenStages.contains(stage.name)
    }

    if (visibleStages.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Alle podia zijn uitgefilterd",
                    color = PinkpopCardText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Gebruik de filterchips hierboven om podia weer in te schakelen.",
                    color = PinkpopMuted,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    // Determine sizing responsive
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val leftRulerWidth = 38.dp
    
    // Dynamic column widths: fit up to 4 stages side-by-side without horizontal scrolling
    val columnWidth = remember(visibleStages.size, screenWidth) {
        val remainingWidth = screenWidth - leftRulerWidth
        if (visibleStages.size <= 4) {
            remainingWidth / maxOf(1, visibleStages.size)
        } else {
            110.dp
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Timetable Container (Time Ruler + Stage Grid)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Main structure with synchronized scrolling
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScrollState)
            ) {
                // Left lock Hour Ruler column
                Column(
                    modifier = Modifier
                        .width(leftRulerWidth)
                        .padding(top = 38.dp) // align with the starting time lines down from columns header
                ) {
                    hoursList.forEach { hour ->
                        Box(
                            modifier = Modifier
                                .height(hourHeight)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            val formattedHour = if (hour >= 24) hour - 24 else hour
                            Text(
                                text = String.format("%02d:00", formattedHour),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 9.5.sp,
                                    lineHeight = 11.sp
                                ),
                                color = PinkpopMuted,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Grid scrollable columns
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(horizontalScrollState, enabled = visibleStages.size > 4)
                ) {
                    visibleStages.forEach { stage ->
                        // Filter artists inside this stage if showOnlyFavorites is true
                        val performancesToRender = if (showOnlyFavorites) {
                            stage.performances.filter { favorites.contains(it.artist) }
                        } else {
                            stage.performances
                        }

                        Column(
                            modifier = Modifier.width(columnWidth),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Column sticky Header displaying stage name (SOUTH STAGE, NORTH STAGE...)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(38.dp)
                                    .padding(horizontal = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stage.name.replace(" X DESPERADOS", "").replace(" (FESTIVAL CAMPING)", "").replace(" (GROEPS CAMPING)", ""),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PinkpopCardText,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 10.5.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Dynamic Coordinate Timetable Line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(totalGridHeight)
                            ) {
                                // Render horizontal lines matching time ruler hour boundaries
                                hoursList.forEachIndexed { index, _ ->
                                    val lineOffset = hourHeight * index
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(y = lineOffset),
                                        thickness = 1.dp,
                                        color = PinkpopGridBg
                                    )
                                }

                                // Vertical columns dividers
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(PinkpopGridBg)
                                        .align(Alignment.CenterEnd)
                                )

                                // Render performance blocks
                                performancesToRender.forEach { perf ->
                                    val isFav = favorites.contains(perf.artist)

                                    val startMins = parseTimeToMinutes(perf.startTime)
                                    var endMins = parseTimeToMinutes(perf.endTime)
                                    if (endMins <= startMins) {
                                        endMins += 24 * 60
                                    }
                                    val duration = endMins - startMins

                                    val topOffsetMins = startMins - (gridStartHour * 60)
                                    val topPixelOffset = (hourHeight * topOffsetMins) / 60
                                    val performanceHeight = (hourHeight * duration) / 60

                                    PerformanceCard(
                                        performance = perf,
                                        isFavorite = isFav,
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp, vertical = 1.dp)
                                            .offset(y = topPixelOffset)
                                            .width(columnWidth - 4.dp)
                                            .height(performanceHeight - 2.dp),
                                        onLongClick = { onArtistLongClick(perf, stage.name) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Pinkpop Footer Bar (matching poster pinkpop skull motif)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PinkpopWhite)
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Small stylized rounded badge representing the legendary Pinkpop flower logo
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PinkpopPink),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌸", fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "pinkpop 19 • 20 • 21 juni 2026",
                    style = MaterialTheme.typography.bodySmall,
                    color = PinkpopPink,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PerformanceCard(
    performance: Performance,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit
) {
    val containerColor = if (isFavorite) PinkpopHighlightBg else PinkpopWhite
    val contentColor = if (isFavorite) PinkpopHighlightText else PinkpopCardText
    val borderColor = if (isFavorite) PinkpopPink else PinkpopGridBg

    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .combinedClickable(
                onClick = {}, // triggers long click focus
                onLongClick = { onLongClick() }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = performance.artist.uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 9.5.sp,
                    lineHeight = 11.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = contentColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${performance.startTime} - ${performance.endTime}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                        lineHeight = 9.5.sp,
                        color = contentColor.copy(alpha = 0.7f)
                    )
                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favoriet",
                            tint = PinkpopPink,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }
        }
    }
}

// Helpers for converting "HH:mm" strings to relative minute indices
fun parseTimeToMinutes(timeStr: String): Int {
    val parts = timeStr.split(":")
    if (parts.size != 2) return 0
    var hour = parts[0].toIntOrNull() ?: 0
    val minute = parts[1].toIntOrNull() ?: 0
    
    // Treat late night/early morning shows (00:00 - 05:00) as extended hours on the current day's calendar
    if (hour in 0..5) {
        hour += 24
    }
    return hour * 60 + minute
}
