package com.lessonmaker.app.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.bottomSheets.SingleOptionModel
import com.lessonmaker.app.common.bottomSheets.SingleOptionSelectSheet
import com.lessonmaker.app.common.extensionFunctions.CountryInfo
import com.lessonmaker.app.common.extensionFunctions.defaultCountry
import com.lessonmaker.app.extensionFunctions.addPlus
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.FlagText
import com.lessonmaker.app.theme.FustatFontFamily
import com.lessonmaker.app.theme.InputFieldBorderClr
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextColor
import com.lessonmaker.app.theme.TextHintColor
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize17
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.border
import com.lessonmaker.app.theme.hintClr
import com.lessonmaker.app.theme.hintTextColor
import com.lessonmaker.app.theme.inputBackgroundColor
import com.lessonmaker.app.theme.inputBorderColorActive
import com.lessonmaker.app.theme.inputBorderColorNotActive
import com.lessonmaker.app.theme.semiBold


import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_down
import lessonmaker.composeapp.generated.resources.arrow_drop_down
import lessonmaker.composeapp.generated.resources.eye_off
import lessonmaker.composeapp.generated.resources.selected_notification_icon
import lessonmaker.composeapp.generated.resources.selected_profile_icon
import lessonmaker.composeapp.generated.resources.unselected_notification_icon
import lessonmaker.composeapp.generated.resources.vector_notification


val roundedShapeForInput = RoundedCornerShape(
    bottomStart = 5.dp,
    bottomEnd = 5.dp,
    topStart = 5.dp,
    topEnd = 5.dp
)
val roundedShape23 = RoundedCornerShape(
    bottomStart = 23.dp,
    bottomEnd = 23.dp,
    topStart = 23.dp,
    topEnd = 23.dp
)

val spaceBetweenFields = 16.dp
val roundedShape5 = RoundedCornerShape(
    bottomStart = 10.dp,
    bottomEnd = 10.dp,
    topStart = 10.dp,
    topEnd = 10.dp
)
val roundedShape30 = RoundedCornerShape(size = 30.dp)
val roundedShape15 = RoundedCornerShape(size = 15.dp)
val roundedShape10 = RoundedCornerShape(size = 10.dp)

@Composable
fun defaultTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,
        placeholderColor = hintTextColor,
        cursorColor = hintTextColor,
        textColor = hintTextColor,
        disabledTextColor = hintTextColor,
    )
}
@Composable
fun SimpleInputField(
    leadingIcon: DrawableResource? = null,
    text: String,
    isLast: Boolean = false,
    enabled: Boolean = true,
    hint: String,
    showClose: Boolean = true,
    keyBoardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, border, shape =  RoundedCornerShape(size = 8.dp))
            .background(inputBackgroundColor, shape =  RoundedCornerShape(size = 8.dp))
            .padding(start = 8.dp, end = 0.dp)
    ) {
        leadingIcon?.let { iconRes ->
            Image(
                painter = painterResource(resource = iconRes),
                contentDescription = "left icon",
                modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp)
            )
        }
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(1f),
            textStyle = TextSize16(),
            singleLine = true,
            placeholder = {
                Text(
                    hint,
                    color = TextHintColor,
                    style = TextSize16(),
                    modifier = Modifier.padding(0.dp)
                )
            },
            enabled = enabled,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent, // Hide the default indicator
                unfocusedIndicatorColor = Color.Transparent, // Hide the default indicator
                cursorColor = TextColor, // Custom cursor color
                textColor = TextColor // Custom text color
            ),
            trailingIcon = {
                if (text.isNotEmpty() && showClose && enabled) {
                    IconButton(onClick = { onValueChange("") }) {
//
//                        Image(
//                            painter = painterResource(resource = Res.drawable.vector_notification),
//                            contentDescription = "Clear text"
//                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = if (isLast) ImeAction.Done else ImeAction.Next
            ),
            interactionSource = interactionSource
        )
    }
}


@Composable
fun MobileNumberBox(dialCode: CountryInfo = defaultCountry, text: String, isLast: Boolean, hint: String="Mobile Number", onValueChange: (String) -> Unit, pickDialCode:((Unit)->Unit)?=null) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val showEnabledColors=isFocused || text.isNotEmpty()
    val borderColor = if (showEnabledColors) border else border


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = inPutFieldModifier(borderColor = borderColor),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier.fillMaxSize().padding(start = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.clickable(interactionSource = MutableInteractionSource(),indication = null){
                    pickDialCode?.invoke(Unit)
                }) {
                    roundedFlagBox(flag = dialCode.flag)
                    Text(
                        text = " ${dialCode.dialCode.addPlus()}",
                        style = TextSize14().bold(),
                        color = Black,
                        modifier = Modifier.wrapContentWidth()
                    )
                }

                TextField(
                    value = text,
                    onValueChange = {
                        if (it.length<10){
                            onValueChange.invoke(it.removePrefix("0"))
                        }
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = InputFieldTextStyle(),
                    singleLine = true,
                    placeholder = {
                        commonHintField(hint = hint)
                    },
                    colors = defaultTextFieldColors(),
                    shape = roundedShapeForInput,
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            inputFieldClear(onValueChange = onValueChange)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = if (isLast){ImeAction.Done}else{ImeAction.Next}
                    ),
                    interactionSource = interactionSource
                )
            }

        }
    }


}
@Composable
fun SimplePasswordInputField(
    leadingIcon: DrawableResource? = null,
    text: String,
    isLast: Boolean = false,
    enabled: Boolean = true,
    hint: String,
    showClose: Boolean = true,
    keyBoardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.10f),
                spotColor = blurClr.copy(alpha = 0.10f)

            )
            .border(
                width = 1.dp,
                color = InputFieldBorderClr,
                shape = RoundedCornerShape(10.dp)
            )

            .background(White, shape = RoundedCornerShape(10.dp))
            .padding(start = 8.dp, end = 0.dp),

    ) {
        leadingIcon?.let { iconRes ->
            Image(
                painter = painterResource(resource = iconRes),
                contentDescription = "left icon",
                modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp)
            )
        }
        // Text field takes remaining space
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(1f),
            textStyle = TextSize16(),
            singleLine = true,
            placeholder = {
                Text(
                    hint,
                    modifier = Modifier.padding(0.dp),
                    color = hintClr,
                    style = TextSize14().Regular()
                )
            },
            enabled = enabled,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent, // Hide the default indicator
                unfocusedIndicatorColor = Color.Transparent, // Hide the default indicator
                cursorColor = TextColor, // Custom cursor color
                textColor = TextColor // Custom text color
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(200)) + scaleIn(tween(200)),
                        exit = fadeOut(tween(200)) + scaleOut(tween(200))
                    ) {
                        val icon = if (isPasswordVisible) {
                            painterResource(Res.drawable.eye_off)

                        } else {
                            painterResource(Res.drawable.eye_off)
                        }

                        if (icon != null) {
                            Image(
                                painter = icon,
                                contentDescription = "Toggle password visibility"
                            )
                        }

                    }
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            interactionSource = interactionSource
        )
    }
}


@Composable
fun SimpleTextInputField2(
    isLast: Boolean = false,
    leadingIcon: DrawableResource? = null,
    endIcon: DrawableResource? = null,
    hint: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp) // Set the height to 47.dp
            .background(White, shape = roundedShapeForInput)
            .border(
                width = 1.dp,
                color = inputBorderColorNotActive, // Replace with your desired border color
                shape = roundedShapeForInput
            )

    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), // Ensure the TextField also has the same height
            textStyle = TextSize15().Medium(),// Match the text style
            singleLine = true,
            placeholder = { Text(hint, color = hintTextColor,modifier = Modifier.padding(0.dp,),
                style = TextSize15()
                ) }
            ,

            colors = defaultTextFieldColors(),
            shape = roundedShapeForInput,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        text = ""
                        onValueChange.invoke("")
                    }) {
//                        Image(
//                            painter = painterResource(resource = Res.drawable.vector_notification),
//                            contentDescription = "Clear text"
//                        )
                    }
                }else {
                    endIcon?.let {
                        Image(
                            painter = painterResource(resource = it),
                            contentDescription = "Clear text"
                        )
                    }
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = if (isLast) ImeAction.Done else ImeAction.Next
            )
        )
    }
}

@Composable
fun SimpleDropDownField(
    isLast: Boolean = false,
    leadingIcon: DrawableResource? = null,
    hint: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 6.dp,
                shape = roundedShapeForInput,
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.15f),
                spotColor = blurClr.copy(alpha = 0.15f)
            )
            .background(White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = InputFieldBorderClr,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), // match outer box
            textStyle = TextSize14().Medium(),
            singleLine = true,
            readOnly = true,
            placeholder = {
                Text(
                    hint,
                    modifier = Modifier.padding(0.dp),
                    color = hintClr,
                    style = TextSize14().Regular()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = roundedShapeForInput,
            trailingIcon = {


            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = if (isLast) ImeAction.Done else ImeAction.Next
            )
        )
    }
}


@Composable
fun SimpleInputFieldWithError(
    text: String,
    isLast: Boolean = false,
    enabled: Boolean = true,
    hint: String,
    showClose: Boolean = true,
    keyBoardType: KeyboardType = KeyboardType.Text,
    errorMessage: String? = null, // Added error message parameter
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Determine colors based on error state first, then focus state
    val borderColor = when {
        errorMessage != null -> Color(0xFFF14336) // Red border on error
        isFocused -> InputFieldBorderClr// Green when focused
        else -> InputFieldBorderClr // Default border
    }

    val iconColor = when {
        errorMessage != null -> Color(0xFFF14336)// Red icon on error
        isFocused -> Color.Transparent// Green when focused
        else -> Color.Transparent // Default hint color
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false,
                    ambientColor = blurClr.copy(alpha = 0.10f),
                    spotColor = blurClr.copy(alpha = 0.10f)

                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(color = Color(0xFFF6F6F6), RoundedCornerShape(10.dp)),
            textStyle = TextSize15(),
            singleLine = true,
            enabled = enabled,
            placeholder = {
                Text(
                    text = hint,
                    color = hintClr,
                    style = TextSize14().Regular()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF6F6F6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = if (errorMessage != null) Color(0xFFF14336)else Color.Companion.Black,
                textColor = Color.Companion.Black,
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                if (text.isNotEmpty() && enabled && showClose) {
                    IconButton(onClick = { onValueChange("") }) {
                        Image(
                            painter = painterResource(resource = Res.drawable.unselected_notification_icon),
                            contentDescription = "Clear text",
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = if (isLast) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle done action */ }
            ),
            interactionSource = interactionSource
        )

    }
}


fun inPutFieldModifier(borderColor: Color): Modifier{
    return Modifier
        .fillMaxWidth().height(50.dp)
        .background(White, roundedShapeForInput)
        .border(1.dp, borderColor, roundedShapeForInput)
}

@Composable
fun roundedFlagBox(flag: String){
    Text(
        text = flag,
        style = FlagText(),
        color = inputBorderColorActive,
        textAlign = TextAlign.Center,
    )
}
@Composable
fun InputFieldTextStyle(): TextStyle {
    val fontFamily = FustatFontFamily()

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = TextHintColor,
        lineHeight = 20.sp
    )

}
@Composable
fun dropDownField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.10f),
                spotColor = blurClr.copy(alpha = 0.10f)
            )
            .border(
                width = 1.dp,
                color = InputFieldBorderClr,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .background(Color.Transparent)
                    .weight(1f),
                placeholder = {
                    Text(
                        text = hint,
                        color = hintClr,
                        style = TextSize14().Regular()
                    )
                },
                readOnly = true,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = TextColor,
                    textColor = TextColor
                ),
            )
            Image(
                painter = painterResource(resource = Res.drawable.arrow_down),
                contentDescription = "drop down",
                modifier = Modifier
                    .size(16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onClick()
                    }
            )
        }
    }
}
@Composable
fun countryPicker(
    value:String,
    hint: String,
    onCoutryChange:(String) -> Unit,
    onValueChange: (String) -> Unit,
    onClick:()-> Unit
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)

            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.10f),
                spotColor = blurClr.copy(alpha = 0.10f)

            )
            .border(
                width = 1.dp,
                color = InputFieldBorderClr,
                shape = RoundedCornerShape(10.dp)
            ),

        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        )
        {
            Text(
                text= "🇦🇪",
            )
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .background(Color.Transparent)
                    .weight(1f),
                placeholder = {
                    Text(

                        text = hint,
                        color = hintClr,
                        style = TextSize14().Regular()
                    )
                },
                readOnly = true,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,   // background
                    focusedIndicatorColor = Color.Transparent,  // remove underline
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = TextColor, // Custom cursor color
                    textColor = TextColor

                ),

                )
            Image(
                painter = painterResource(resource = Res.drawable.arrow_down),
                contentDescription = "drop down",
                modifier = Modifier

                    .clickable(interactionSource = MutableInteractionSource(),
                        indication = null)
                    {
                        onClick.invoke()
                    }
                    .size(16.dp)

            )

        }

    }

}

@Composable
fun ageAskingField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.10f),
                spotColor = blurClr.copy(alpha = 0.10f)
            )
            .border(
                width = 1.dp,
                color = InputFieldBorderClr,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,

                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = hint,
                        color = hintClr,
                        style = TextSize14().Regular(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,

                textStyle = TextSize15().semiBold().copy(textAlign = TextAlign.Center),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),



                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = TextColor,
                    textColor = TextColor
                ),
            )
        }
    }
}



@Composable
fun SingleOptionPicker(oldSelected: SingleOptionModel?,
                       optionsToSelect:ArrayList<SingleOptionModel>,
                       hint: String,
                       endIcon: DrawableResource?=null,
                       onSelected:(SingleOptionModel?)->Unit){
    val showBottomSheet = remember { mutableStateOf(false) }
    ClickableSimpleField(
        text = oldSelected?.name.handleNull(),
        hint = hint,
        endIcon = endIcon,
        onCleared = {
            onSelected.invoke(null)
            showBottomSheet.value = false
        },
        startIcon = null,
        onClick = {
            showBottomSheet.value = true
        }
    )
    if (showBottomSheet.value){
        SingleOptionSelectSheet(
            oldSelectedOption = oldSelected,
            optionsToSelect =  optionsToSelect,
            onDismiss = {
                showBottomSheet.value = false
            },
            selectedApp = {
                showBottomSheet.value = false
                onSelected.invoke(it)
            },

            )

    }
}

@Composable
fun ClickableSimpleField(
    text: String, hint: String,
    startIcon: DrawableResource? = null,
    endIcon: DrawableResource? = null,
    isLast: Boolean = false,
    onCleared: (String) -> Unit,
    onClick: ((Unit) -> Unit)? = null
)
{

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Border color based on focus state
    val borderColor = if (isFocused) White else White

     TextField(
        value = text,
        onValueChange = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, White, shape = RoundedCornerShape(10.dp))
            .background(color = White, shape = RoundedCornerShape(10.dp))
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                onClick?.invoke(Unit)
            },
        textStyle = TextStyle(
            fontSize = 15.sp,
            color = Black,
            lineHeight = 20.sp
        ),
        singleLine = true,
        enabled = false,
        placeholder = {
            Text(
                text = hint,
                color = hintClr,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent, // Hide the default indicator
            unfocusedIndicatorColor = Color.Transparent, // Hide the default indicator
            cursorColor = Black, // Custom cursor color
            textColor = Black, // Custom text color

            disabledIndicatorColor = Color.Transparent, // ← ADD THIS LINE
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    onCleared.invoke("")
                }) {
                    Image(
                        painter = painterResource(resource = Res.drawable.vector_notification),
                        contentDescription = "Clear text"
                    )
                }
            } else {
                endIcon?.let {
                    Image(
                        painter = painterResource(resource = it),
                        contentDescription = "Clear text"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        interactionSource = interactionSource
    )

}


@Composable
fun ClickableSimpleFieldStartIcon(
    text: String, hint: String,
    startIcon: DrawableResource? = null,
    endIcon: DrawableResource? = null,
    isLast: Boolean = false,
    onCleared: (String) -> Unit,
    onClick: ((Unit) -> Unit)? = null
)
{

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Border color based on focus state
    val borderColor = if (isFocused) White else White

     TextField(
        value = text,
        onValueChange = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, White, shape = RoundedCornerShape(10.dp))
            .background(color = White, shape = RoundedCornerShape(10.dp))
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                onClick?.invoke(Unit)
            },
        textStyle = TextStyle(
            fontSize = 15.sp,
            color = Black,
            lineHeight = 20.sp
        ),
        singleLine = true,
        enabled = false,
        placeholder = {
            Text(
                text = hint,
                color = hintClr,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent, // Hide the default indicator
            unfocusedIndicatorColor = Color.Transparent, // Hide the default indicator
            cursorColor = Black, // Custom cursor color
            textColor = Black, // Custom text color

            disabledIndicatorColor = Color.Transparent, // ← ADD THIS LINE
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    onCleared.invoke("")
                }) {
                    Image(
                        painter = painterResource(resource = Res.drawable.vector_notification),
                        contentDescription = "Clear text"
                    )
                }
            } else {
                endIcon?.let {
                    Image(
                        painter = painterResource(resource = it),
                        contentDescription = "Clear text"
                    )
                }
            }
        },
         leadingIcon = {
             startIcon?.let { iconResource ->
                 Image(
                     painter = painterResource(resource = iconResource),
                     contentDescription = "Start icon"
                 )
             }
         },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        interactionSource = interactionSource
    )

}

@Composable
fun commonHintField(hint: String,color: Color= TextHintColor){
    Text(
        text = hint,
        color = color,
        style = TextSize16(),
        maxLines = 1,
        modifier = Modifier.fillMaxWidth()
    )
}



@Composable
fun inputFieldClear(onValueChange: (String) -> Unit){
    IconButton(onClick = { onValueChange("") }) {

//        Image(
//            painter = painterResource(resource = Res.drawable.vector_notification),
//            contentDescription = "Clear text",
//            colorFilter = ColorFilter.tint(inputBorderColorActive)
//        )
    }
}
