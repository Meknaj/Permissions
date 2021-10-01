package com.applibgroup.permission;

import com.applibgroup.permission.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.window.dialog.ToastDialog;
import ohos.bundle.IBundleManager;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsFromUserResult(requestCode, permissions, grantResults);
        if (requestCode == MainAbilitySlice.REQUEST_CODE_READ_USER_STORAGE
                && grantResults[0] == IBundleManager.PERMISSION_GRANTED) {
            MainAbilitySlice.queryUserStorage(this);
        } else {
            new ToastDialog(this).setText("Permission denied").show();
        }
    }
}
