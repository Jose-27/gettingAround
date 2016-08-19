package com.example.joe.goout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by joe on 8/6/16.
 */
public class apiReader extends AsyncTask<Void,Void,Void>{
    Context context;
    String address="https://www.nycgovparks.org/xml/events_300_rss.xml";
    ProgressDialog progressDialog;
    ArrayList<FeedItem>feedItems;
    RecyclerView recyclerView;
    URL url;

    public  apiReader(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.context=context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter=new MyAdapter(context,feedItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXml(Getdata());
        return null;
    }

    private void ProcessXml(Document data) {
        if (data!= null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++){
                Node currentchild = items.item(i);
                if(currentchild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item=new FeedItem();
                    NodeList itemchilds = currentchild.getChildNodes();
                    for(int j = 0;j < itemchilds.getLength(); j++){
                        Node current = itemchilds.item(j);
                        if(current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("event:parknames")){
                            item.setParkNames(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            Spanned result;
                            //**Checks for android version**\\
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                //**Removes all the html tags and characters**\\
                                result = Html.fromHtml(current.getTextContent(),Html.FROM_HTML_MODE_LEGACY);
                            } else {
                                result = Html.fromHtml(current.getTextContent());
                            }
                            item.setDescription(String.valueOf(result));

                        }else  if(current.getNodeName().equalsIgnoreCase("event:image")){
                            //String url = current.getTextContent();
                            item.setImage(current.getTextContent().toString());
                        }else if(current.getNodeName().equalsIgnoreCase("event:startDate")){
                            item.setStartDate(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("event:location")){
                            item.setLocation(current.getTextContent());
                        }
                    }
                    feedItems.add(item);
                }
            }
        }
    }

    public Document Getdata(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
