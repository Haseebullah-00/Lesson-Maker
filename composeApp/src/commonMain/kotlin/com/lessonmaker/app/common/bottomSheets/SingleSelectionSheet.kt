package com.lessonmaker.app.common.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.extensionFunctions.CustomCheckBox
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.blackColor510_30
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.greyF3
import com.lessonmaker.app.theme.inputBorderColorNotActive
import com.lessonmaker.app.theme.listCardBgColor

import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_next
import lessonmaker.composeapp.generated.resources.option_selected


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleOptionSelectSheet(
    oldSelectedOption: SingleOptionModel?,
    optionsToSelect: ArrayList<SingleOptionModel>,
    onDismiss: () -> Unit,
    selectedApp: (SingleOptionModel) -> Unit,
    hint: String? = null // 👈 optional hint
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredData = optionsToSelect.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    BaseBottomSheet(
        fullScreenDefault = false,
        heading = hint ?: "Select option", // 👈 use provided or fallback
        subHeading = "",
        bottomSheetCommonInterface = object :
            BottomSheetCommonInterface {
            override fun closeSheet() {
                onDismiss.invoke()
            }
        }) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
            /*SearchTextInputField(
                text = searchQuery,
               leadingIcon = Res.drawable.ic_search,
                hint = "Search",
                onValueChange = {
                    searchQuery = it
                })
            Spacer(modifier = Modifier.height(8.dp))*/
            LazyColumn(contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)) {
                items(filteredData.size) { app ->
                    val currentOption = filteredData[app]
                    singleOptionUI(
                        isLast = app == filteredData.size - 1,
                        singleOptionModel = currentOption,
                        isSelected = currentOption.id.equals(oldSelectedOption?.id)
                    ) {
                        selectedApp.invoke(it)
                    }
                }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun multiSelectOptionSelectSheet(
    oldSelectedOption: ArrayList<SingleOptionModel>,
    optionsToSelect: ArrayList<SingleOptionModel>,
    onDismiss: () -> Unit,
    selectedApp: (SingleOptionModel, Boolean) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredData = optionsToSelect.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    BaseBottomSheet(
        fullScreenDefault = false,
        heading = "Select option",
        subHeading = "",
        bottomSheetCommonInterface = object :
            BottomSheetCommonInterface {
            override fun closeSheet() {
                onDismiss.invoke()
            }
        }) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
            /*SearchTextInputField(
                text = searchQuery,
                leadingIcon = Res.drawable.ic_search,
                hint = "Search",
                onValueChange = {
                    searchQuery = it
                })
            Spacer(modifier = Modifier.height(8.dp))*/
            LazyColumn(contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)) {
                items(filteredData.size) { app ->
                    val currentOption = filteredData[app]
                    val isSelect = oldSelectedOption.any { it.id.equals(filteredData[app].id) }
                    singleOptionUI(
                        isLast = app == filteredData.size - 1,
                        singleOptionModel = currentOption,
                        isSelected = isSelect
                    ) {
                        selectedApp.invoke(it, isSelect.not())
                    }
                }
            }
        }

    }


}


@Composable
fun singleOptionUI(
    isLast: Boolean = false,
    isSelected: Boolean,
    singleOptionModel: SingleOptionModel,
    clicked: (SingleOptionModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Disables the ripple effect
                ) {
                    clicked.invoke(singleOptionModel)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                singleOptionModel.name.handleNull() ,
                modifier = Modifier.weight(1f).padding(vertical = 5.dp),
                style = TextSize14(),
                color = blackColor510_30
            )
            if (isSelected) {
                Image(
                    painter = painterResource(Res.drawable.option_selected),
                    contentDescription = "Selected",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
        if (isLast.not()) {
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 1.dp,
                color = listCardBgColor
            )
        }
    }
}


@Composable
fun singleOptionUIWithArrow(
    isLast: Boolean = false,
    singleOptionModel: SingleOptionModel,
    clicked: (SingleOptionModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Disables the ripple effect
                ) {
                    clicked.invoke(singleOptionModel)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                singleOptionModel.name.handleNull(),
                modifier = Modifier.weight(1f).padding(vertical = 5.dp),
                style = TextSize14(),
                color = blackColor510_30
            )
            Image(
                painter = painterResource(Res.drawable.ic_next),
                contentDescription = "Selected",
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit
            )
        }
        if (isLast.not()) {
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 1.dp,
                color = listCardBgColor
            )
        }
    }
}

@Composable
fun singleOptionUIWithCheck(
    isChecked: Boolean = false,
    singleOptionModel: SingleOptionModel,
    inFoTag: String? = null,
    clicked: (SingleOptionModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (singleOptionModel.slag.isNullOrBlank().not()) {
            Text(
                text = singleOptionModel.slag.handleNull(),
                color = Black,
                style = TextSize18().bold(),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Disables the ripple effect
                ) {
                    clicked.invoke(singleOptionModel)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = singleOptionModel.name.handleNull(),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextSize14(),
                    color = blackColor510_30
                )
                inFoTag?.let {
                    Text(
                        text = it.handleNull(),
                        modifier = Modifier.fillMaxWidth(),
                        style = TextSize11(),
                        color = inputBorderColorNotActive
                    )
                }
            }
            CustomCheckBox(
                isChecked = isChecked,
                shape = CircleShape
            ) {
                clicked.invoke(singleOptionModel)
            }
        }
        Divider(modifier = Modifier, thickness = 1.dp, color = listCardBgColor)
    }
}


@Composable
fun singleOptionUIWithWithColor(
    isChecked: Boolean = false,
    singleOptionModel: SingleOptionModel,
    clicked: (SingleOptionModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (singleOptionModel.slag.isNullOrBlank().not()) {
            Text(
                text = singleOptionModel.slag.handleNull(),
                color = Black,
                style = TextSize18().bold(),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Disables the ripple effect
                ) {
                    clicked.invoke(singleOptionModel)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val shape = RoundedCornerShape(4.dp)
            Box(
                modifier = Modifier.size(24.dp).clip(shape = shape)
                    .background(color = singleOptionModel.rawData as Color, shape = shape)
                    .border(width = 1.dp, shape = shape, color = greyF3)
            )
            Text(
                singleOptionModel.name.handleNull(),
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 15.dp, vertical = 8.dp),
                style = TextSize14(),
                color = blackColor510_30
            )
            CustomCheckBox(
                isChecked = isChecked
            ) {
                clicked.invoke(singleOptionModel)
            }
        }
        Divider(modifier = Modifier, thickness = 1.dp, color = listCardBgColor)
    }
}


data class SingleOptionModel(
    var id: String,
    var name: String,
    var rawData: Any? = null,
    var slag: String? = null
)
