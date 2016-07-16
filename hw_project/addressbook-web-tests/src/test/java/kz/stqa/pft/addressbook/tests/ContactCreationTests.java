package kz.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.Contacts;
import kz.stqa.pft.addressbook.model.GroupData;
import kz.stqa.pft.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null){
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoGroupPage();
        if (!app.group().isThereAGroup()) {
            app.group().createGroup(new GroupData().withName("test1"));
        }
        app.goTo().returnToMainPage();
    }

    
    @Test (dataProvider = "validGroupsFromJson")
    public void testContactCreation(ContactData contacts) {
        //Contacts before = (Contacts) app.contact().all();
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        app.goTo().gotoContactPage();
       ContactData contact = new ContactData().withFirst_name("Артем").withSecond_name("Петрович").withLast_name("Кузьмин").withCompany("ложки.ком").withPhone("+89654123654")
               .inGroup(groups.iterator().next());
        app.contact().createContact(contacts);
        Contacts after = app.db().contacts();
        //Contacts after = app.contact().all();
        //assertThat(after.size(), equalTo(before.size() +1));
        assertThat(after, equalTo(before.withAdded(contacts.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    }
}
