package funhacks.curry29.morauknow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

public class EventList extends AppCompatActivity {
    private ListView lv;
    List<ListItem> list = new ArrayList<ListItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NCMB.initialize(this.getApplicationContext(),"fe7bee8bea8475ddbecdbf020d6ec93c2dfb2bb6c857c33f16191eb9ce10ab19","3c50c489b02de8566548de932cadd64f75bbd7127039c1981bfe7652e15c8572");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

       load();
        lv = (ListView) findViewById(R.id.listview);


        //リスト項目が選択されたときのイベントを追加
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = position + "番目のアイテムがクリックされました";
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void load(){
        NCMBQuery<NCMBObject> query	=new NCMBQuery<>("EventList");
        //データストアからデータを検索
        query.findInBackground(new	FindCallback<NCMBObject>(){
            @Override
            public	void done(List<NCMBObject> results, NCMBException e)	{
                if(e != null){
                    Log.d("load()", "NG");
                    //load();
                }else {
                    for (NCMBObject result : results) {
                        ListItem item = new ListItem();
                        int imagename = getResources().getIdentifier(result.getString("imageURL"),"drawable",getPackageName());
                        item.setEventName(result.getString("EventName"));
                        item.setTime(result.getString("Time"));
                        item.setArea(result.getString("AreaName"));
                        item.setImageURL(imagename);
                        Log.d("Test", "done:"+item.getArea());
                        list.add(item);
                    }
                    ImageArrayAdapter adapter = new ImageArrayAdapter(getApplication(), R.layout.list_view_image_item, list);
                    lv.setAdapter(adapter);
                }
            }
        });
    }
}