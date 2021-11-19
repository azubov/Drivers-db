package ru.lanit.driversdb.service.licenses;

import generated.LicenseType;
import generated.PersonType;

public interface LicensesService {

    void addLicenseToDriver(PersonType driver, LicenseType license);
    LicenseType findDriversLicenseById(PersonType driver, String licenseId);
    void removeLicenseFromDriverById(PersonType driver, String licenseId);
}
