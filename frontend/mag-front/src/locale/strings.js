import i18n from 'i18n-js'

i18n.translations = {
    en: {
        L_HOME: "Home",

        B_SIGN: 'Sign ',
        B_SIGN_UP: 'Sign Up',
        B_SIGN_IN: 'Sign In',

        L_SIGN: 'Sign',
        L_LOGIN: 'Login',
        L_SIGN_UP: 'Sign Up',
        L_S_EM_UN: 'E-mail/Username',
        L_S_UN: 'Username',
        L_S_PW: 'Password',
        L_S_EM: "E-mail",
        L_S_PWC: 'Confirm password',
        L_S_FN: 'First name',
        L_S_GENDER: 'Gender',

        L_P_PROFILE: 'Profile',
        L_P_USER: 'User',
        L_P_STAFF: 'Staff Member',
        L_P_ACTIVE: 'Active',
        L_P_SUSPENDED: 'Suspended',
        L_P_DB: 'Go to Database',
        L_P_LOGOUT: 'Logout',

        L_DB_OPR: "Operation",
        L_DB_ADD: "Add",
        L_DB_UPD: "Update",
        L_DB_DEL: "Delete",
        L_DB_CLOSE: "Close",

        L_HOLDER_EM_UN: 'Enter E-mail/Username',
        L_HOLDER_UN: 'Enter username',
        L_HOLDER_EM: 'Enter E-mail',
        L_HOLDER_PW: 'Enter password',
        L_HOLDER_PWC: 'Enter password',
        L_HOLDER_FN: 'Enter first name',

        L_E_EM_UN_ETY: 'Please enter E-mail or Username',
        L_E_UN_ETY: 'Please enter username',
        L_E_UN_FMT: 'Username must be 6-30 characters long and may contain letters, numbers and \'_\'',
        L_E_UN_SAME: "User with this username already exists",
        L_E_EM_SAME: "User with this E-mail already exists",
        L_E_PW_ETY: 'Please enter password',
        L_E_PW_FMT: 'Password must be 8-16 characters long, contain letters and numbers, and must not contain spaces and special characters',
        L_E_EM_ETY: 'Please enter your E-mail',
        L_E_EM_FMT: 'Please enter E-mail in valid format',
        L_E_PWC_ETY: 'Please confirm your password',
        L_E_PWC_S: 'Entered passwords must be same',
        L_E_FN_ETY: 'Please enter your first name',
        L_E_FN_FMT: 'First name max length is 30, only alphabetic and numeric characters (spaces allowed)',

        MSG_E_SI_EM: 'Invalid E-mail/Password',
        MSG_E_SI_UN: 'Invalid Username/Password',
        MSG_E_SU_DUP: 'Account with this E-mail/Username already registered',

        ACC_USERNAME: 'Username',
        ACC_EMAIL: 'E-Mail',
        ACC_PW: 'Password',
        ACC_PWC: 'Confirm password',
        ACC_NPW: 'New Password',
        ACC_FNAME: 'First Name',
        ACC_LNAME: 'Last Name',
        ACC_BIRTH: 'Birth Date',
        ACC_GENDER: 'Gender',
        ACC_ABOUT: 'About',

        ALB_USERNAME: 'Account Username',
        ALB_EMAIL: 'Account E-Mail',
        ALB_NAME: 'Album Name',
        ALB_DESCR: 'Description',

        PUB_USERNAME: 'Account Username',
        PUB_EMAIL: 'Account E-Mail',
        PUB_ALBNAME: 'Album Name',
        PUB_GENNAME: 'Genre',
        PUB_DATE: 'Publication Date',
        PUB_NAME: 'Publication Name',
        PUB_ITEM: 'Select Item',
        PUB_NOTE: 'Note',
        PUB_TEXT: 'Note text',
        PUB_PICTURE: 'Picture',
        PUB_IMAGE: 'Select Image',
        PUB_PIC_DESCR: 'Image description',
        FILE_HOLDER: 'File...',
        PUB_BROWSE: 'Browse',

        ACC_UN_E: "Username must be 1-30 characters long and may contain letters, numbers and '_'",
        ACC_EM_E: "Please enter E-mail in valid format",
        ACC_PW_E: "Password must be 8-16 characters long, contain letters and numbers, and must not contain spaces and special characters",
        ACC_PWC_E: "Entered passwords must be same",

        ALB_NAME_E: "Album name must be 1-50 characters long and may contain letters, numbers and '_'",
        PUB_NAME_E: "Publication name must be 1-60 characters long and shouldn't contain special characters",
        PUB_TXT_E: "Please enter text to your publication",
        PUB_IMG_E: "Please choose image from your computer",

        GEN_NAME: 'Genre name',
        N_GEN_NAME: 'New Genre name',

        GEN_NAME_E: "Genre name must be 1-50 characters (only letters)",

        E_MSG_ETY: 'Please enter ',
        E_MSG_NOFILE: 'Please choose a image file',

        MSG_PROCEED: '{operation} Proceed',
        E_MSG_DUP: 'Error: {entityType} with {fields} already in database',
        E_MSG_NF: 'Error: Corresponding {entityType} not found'
    }
}

i18n.locale = "en";
i18n.fallbacks = true;

export function strings(name) {
    return i18n.t(name, {});
}

export function format(string, placeholders) {
    let s = string;
    for (let propName in placeholders) {
        let re = new RegExp('{' + propName + '}', 'gm');
        if (placeholders.hasOwnProperty(propName)){
            s = s.replace(re, placeholders[propName]);
        }
    }
    return s;
}