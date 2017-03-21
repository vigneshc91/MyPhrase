package com.phrase.my.myphrase;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class PasswordViewListActivity extends MenuActivity {

    private FloatingActionButton addNewPassword;
    private ListView detailListView;
    DetailListAdapter detailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_view_list);

        addNewPassword = (FloatingActionButton) findViewById(R.id.addNewPasswordBtn);

        addNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewPasswordIntent = new Intent(PasswordViewListActivity.this, AddPasswordActivity.class);
                startActivity(addNewPasswordIntent);
            }
        });

        setListAdapter();

        detailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Object obj = detailListView.getItemAtPosition(position);
                Detail detail = (Detail) obj;

                Intent viewPasswordIntent = new Intent(PasswordViewListActivity.this, ViewPasswordActivity.class);
                viewPasswordIntent.putExtra("detail_id", detail.getId().toString());
                startActivity(viewPasswordIntent);
            }
        });

        detailListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        detailListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int checkedItemCount =  detailListView.getCheckedItemCount();
                mode.setTitle(checkedItemCount + " selected");
                detailListAdapter.toggleSelection(position);
                if(checked){
                    detailListView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.listHighlight));
                } else {
                    detailListView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.deletePassword:
                        AlertDialog.Builder deleteAlertBuilder = new AlertDialog.Builder(PasswordViewListActivity.this);
                        deleteAlertBuilder.setTitle(ErrorConstants.ALERT);
                        deleteAlertBuilder.setMessage(ErrorConstants.WANT_TO_DELETE_PASSWORDS);
                        deleteAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SparseBooleanArray selected = detailListAdapter.getSelectedDetailIds();
                                for (int i=0; i<selected.size(); i++){
                                    if(selected.valueAt(i)){
                                        Detail selectedItem = detailListAdapter.getItem(selected.keyAt(i));
                                        detailListAdapter.remove(selectedItem);
                                    }
                                }
                                mode.finish();
                            }
                        });
                        deleteAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog deleteAlert = deleteAlertBuilder.create();
                        deleteAlert.show();

                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for (int i=0; i<detailListView.getChildCount(); i++){
                    View view = detailListView.getChildAt(i);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                detailListAdapter.removeSelection();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListAdapter();
    }

    private void setListAdapter(){
        detailListView = (ListView) findViewById(R.id.passwordListView);
        List<Detail> detailList = DbOperations.getAllDetails();

        detailListAdapter = new DetailListAdapter(this, detailList);
        detailListView.setAdapter(detailListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem editMenuItem = menu.findItem(R.id.menuEditDetail);
        editMenuItem.setVisible(false);
        MenuItem deleteMenuItem = menu.findItem(R.id.menuDeleteDetail);
        deleteMenuItem.setVisible(false);

        // Search Filter
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.passwordSearchMenu);
        searchMenuItem.setVisible(true);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                detailListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }
}
