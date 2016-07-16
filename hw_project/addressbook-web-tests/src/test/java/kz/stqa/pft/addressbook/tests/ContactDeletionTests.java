package kz.stqa.pft.addressbook.tests;

import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.Contacts;
import kz.stqa.pft.addressbook.model.GroupData;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by xeniya on 6/2/16.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (! app.contact().isThereContact()) {
            if (!app.group().isThereAGroup()) {
                app.goTo().gotoGroupPage();
                app.group().createGroup(new GroupData().withName("test1"));
                app.goTo().gotoContactPage();
            }
            app.contact().createContact(new ContactData().withFirst_name("Петр").withSecond_name("Петрович").withLast_name("Кузьмин").withCompany("ложки.ком").withPhone("+89654123654"));
        }
    }

    @Test
    public void  testContactDeletion(){
        Contacts before = app.db().contacts();
        //Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().deleteContact(deletedContact);
        app.goTo().returnToMainPage();
        Contacts after = app.db().contacts();
        //Contacts after = app.contact().all();
        //assertEquals(after.size(), before.size() - 1);

        assertThat(after, CoreMatchers.equalTo(before.without(deletedContact)));

    }
}
