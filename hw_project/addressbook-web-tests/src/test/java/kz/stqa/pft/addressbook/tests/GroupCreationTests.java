package kz.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import kz.stqa.pft.addressbook.model.Contacts;
import kz.stqa.pft.addressbook.model.GroupData;
import kz.stqa.pft.addressbook.model.Groups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        try( BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))){
        String xml = "";
        String line = reader.readLine();
        while (line != null){
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
            }.getType());
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData group) {
        //GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
        app.goTo().gotoGroupPage();
        Groups before = app.db().groups();
        //Groups before = (Groups) app.group().all();
        app.group().createGroup(group);
       // assertThat(app.group().getGroupCount(), equalTo(before.size() + 1));
        Groups after = app.db().groups();
        //Groups after = (Groups) app.group().all();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
        verifyGroupListInUI();

    }

    @Test (enabled = false)
    public void testBadGroupCreation() {
        app.goTo().gotoGroupPage();
        //Groups before = (Groups) app.group().all();
        Groups before = app.db().groups();
        GroupData group = new GroupData().withName("test2'");
        //int before = app.group().getGroupCount();
        app.group().createGroup(group);
        //int after = app.group().getGroupCount();
        //assertThat(app.group().getGroupCount(), equalTo(before.size()));
        Groups after = app.db().groups();
        //Groups after = (Groups) app.group().all();
        assertThat(after, equalTo(before));

    }

}
