package ca.mcgill.purposeful.util;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.model.AppUser;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class CucumberUtil {


    public static ArrayList<AppUser> unpackTableIntoUser(DataTable dataTable) {
        // get access to the data table
        List<Map<String, String>> rows = dataTable.asMaps();

        // create an empty list of app users
        ArrayList<AppUser> appUsers = new ArrayList<AppUser>();

        for (var row : rows) {
            // get the values from the data table
            String firstname = row.get("firstname");
            String lastname = row.get("lastname");
            String email = row.get("email");
            String password = row.get("password");
            String authorities = row.get("authorities");
            Authority authority = Authority.valueOf(authorities);

            // create an instance of App User with these properties
            AppUser appUser = new AppUser();
            appUser.setFirstname(firstname);
            appUser.setLastname(lastname);
            appUser.setEmail(email);
            appUser.setPassword(password);

            // set the athorities of the app user
            Set<Authority> setOfAuthorities = new HashSet<Authority>();
            setOfAuthorities.add(authority);
            appUser.setAuthorities(setOfAuthorities);

            // add the app user to the list of app users
            appUsers.add(appUser);
        }
        return appUsers;
    }
}
