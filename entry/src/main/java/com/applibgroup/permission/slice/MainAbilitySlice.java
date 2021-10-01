package com.applibgroup.permission.slice;

import com.applibgroup.permission.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.bundle.IBundleManager;
import ohos.data.resultset.ResultSet;
import ohos.media.photokit.metadata.AVStorage;
import ohos.security.SystemPermission;

public class MainAbilitySlice extends AbilitySlice {

    public static final int REQUEST_CODE_READ_USER_STORAGE = 101;
    Button load;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initComponents();
    }

    public void initComponents() {
        load = (Button) findComponentById(ResourceTable.Id_text_load);
        load.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                loadImageCount();
            }
        });
    }

    public void loadImageCount() {
        requestPermission(SystemPermission.READ_USER_STORAGE, REQUEST_CODE_READ_USER_STORAGE);
    }

    public void requestPermission(String permission, int reqCode) {
        if (verifySelfPermission(permission) == IBundleManager.PERMISSION_GRANTED) {
            queryUserStorage(this);
            return;
        }
        if (!canRequestPermission(permission)) {
            new ToastDialog(this).setText("Cannot request the permission").show();
            return;
        }
        requestPermissionsFromUser(new String[]{permission}, reqCode);
    }

    public static void queryUserStorage(Context context) {
        DataAbilityHelper helper = DataAbilityHelper.creator(context);
        try {
            ResultSet resultSet = helper.query(AVStorage.Images.Media.EXTERNAL_DATA_ABILITY_URI,
                    new String[]{AVStorage.Images.Media.ID}, null);
            int count = resultSet.getRowCount();
            new ToastDialog(context).setText("Total Image Count = " +count).show();
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
