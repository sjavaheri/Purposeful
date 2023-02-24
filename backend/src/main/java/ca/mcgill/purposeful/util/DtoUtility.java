package ca.mcgill.purposeful.util;

import org.springframework.http.HttpStatus;

import ca.mcgill.purposeful.dto.*;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.*;

public class DtoUtility {

  /**
   * Converts a AppUser to a AppUserDto
   *
   * @param appUser - the AppUser to be converted
   * @return the converted AppUserDto
   * @author Siger Ma
   */

  public static AppUserDto convertToDto(AppUser appUser) {
    if (appUser == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "AppUser is null");
    }

    // Convert AppUser to AppUserDto
    AppUserDto appUserDto = new AppUserDto();
    appUserDto.setId(appUser.getId());
    appUserDto.setEmail(appUser.getEmail());
    appUserDto.setFirstname(appUser.getFirstname());
    appUserDto.setLastname(appUser.getLastname());
    appUserDto.setPassword("");
    return appUserDto;
  }
}
