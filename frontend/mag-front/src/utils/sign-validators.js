import validator from "validator/es";
const username_char_regex = /^[a-zA-Z0-9_]+$/su;
const password_char_regex = /^[a-zA-Z0-9_$?!<>]+$/su;
const first_name_char_regex = /^[a-zA-Z0-9 ]+$/su;

const functionMap = {
    userId: validateUId,
    email: validateEmail,
    username: validateUsername,
    password: validatePassword,
    passwordConf: validatePasswordConf,
    firstName: validateFirstName
}

function validateField(fieldName, fieldValue) {
    return functionMap[fieldName](fieldValue);
}

function validateUId(value){
    const val = value.trim();
    if (validator.isEmpty(val)){
        return {uid_empty: true, em_invalid: true, un_invalid: true};
    }
    if (validator.contains(val, "@")){
        const validMap = validateEmail(val);
        if (validMap.em_invalid){
            return {uid_empty: false, un_invalid: false, em_invalid: true}
        }
    } else {
        const validMap = validateUsername(val);
        if (validMap.un_invalid){
            return {uid_empty: false, un_invalid: true, em_invalid: false}
        }
    }
    return {uid_empty: false, un_invalid: false, em_invalid: false}
}

function validateEmail(value){
    if (!value || typeof value !== "string" || validator.isEmpty(value.trim())){
        return {em_empty: true, em_invalid: true};
    } else if (!validator.isEmail(value)){
        return {em_empty: false,em_invalid: true};
    }
    return {em_empty: false, em_invalid: false};
}

function validateUsername(value) {
    if (!value || typeof value !== "string" ){
        return {un_empty: true, un_invalid: true};
    }
    const val = value.trim();
    if (validator.isEmpty(val)){
        return {un_empty: true, un_invalid: true};
    }
    if (!validator.isLength(val, {min: 6, max: 32}) || !username_char_regex.test(val)){
        return {un_empty: false, un_invalid: true};
    }
    return {un_empty: false, un_invalid: false};
}

function validatePassword(value) {
    if (!value || typeof value !== "string" || validator.isEmpty(value)){
        return {pw_empty: true, pw_invalid: true};
    }
    if (!validator.isLength(value, {min: 8, max: 16}) || !password_char_regex.test(value)){
        return {pw_empty: false, pw_invalid: true};
    }
    return {pw_empty: false, pw_invalid: false};
}

function validatePasswordConf(value){
    if (!value || typeof value !== "string" || validator.isEmpty(value)){
        return {pwc_empty: true, pwc_invalid: false};
    }
    return {pwc_empty: false, pwc_invalid: false};
}

function validateFirstName(value) {
    if (!value || typeof value !== "string"){
        return {fn_empty: true, fn_invalid: true};
    }
    const val = value.trim();
    if (validator.isEmpty(val)){
        return {fn_empty: true, fn_invalid: true};
    }
    if (!validator.isLength(val, {min: 1, max: 28}) || !first_name_char_regex.test(val)){
        return {fn_empty: false, fn_invalid: true};
    }
    return {fn_empty: false, fn_invalid: false};
}

function passwordsIsEquals(password, passwordConf) {
    if (!password || typeof password !== "string" || !passwordConf || typeof passwordConf !== "string") {
        return false;
    }
    return validator.equals(password, passwordConf)
}

function uidIsEmail(value) {
    return (validator.contains(value, "@"));
}

export {validateField, passwordsIsEquals, uidIsEmail}
