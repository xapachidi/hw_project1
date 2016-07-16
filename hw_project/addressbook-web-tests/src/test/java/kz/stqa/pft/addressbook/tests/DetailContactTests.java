package kz.stqa.pft.addressbook.tests;

import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.GroupData;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by xeniya on 6/22/16.
 */
public class DetailContactTests extends TestBase {

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
    public void testDetailContact(){
        ContactData contact = app.contact().all().iterator().next();

       assertThat(app.contact().mergeInfo(app.contact().infoFromEditForm(contact)), equalTo(app.contact().info(contact)));

    }

}
