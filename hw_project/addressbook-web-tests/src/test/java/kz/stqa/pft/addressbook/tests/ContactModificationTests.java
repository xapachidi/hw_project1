package kz.stqa.pft.addressbook.tests;


import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.Contacts;
import kz.stqa.pft.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by xeniya on 6/2/16.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (! app.contact().isThereContact()) {
            if (!app.group().isThereAGroup()) {
                app.goTo().gotoGroupPage();
                app.group().createGroup(new GroupData().withName("test1"));
                app.goTo().gotoContactPage();
            }
            app.contact().createContact(new ContactData().withFirst_name("Петр").withSecond_name("Петрович").withLast_name("Кузьмин").withCompany("ложки.ком").withPhone("+89654123654").withCompany("test1"));
        }
    }

    @Test
    public void testContactModification(){
       Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        //app.contact().selectContact(before.size() -1);
        //app.contact().initContactModification(before.size()-1);
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirst_name("Игорь").withSecond_name("Петрович").withLast_name("Кузьмин").withCompany("ложки.ком").withPhone("+89654123654");
        //app.goTo().returnToMainPage();
        app.contact().modifyContact(contact);
        Contacts after = app.db().contacts();
        //assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }
}
