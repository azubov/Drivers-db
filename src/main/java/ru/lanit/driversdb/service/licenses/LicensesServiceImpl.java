package ru.lanit.driversdb.service.licenses;

import generated.LicenseType;
import generated.PersonType;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.service.UUIDGenerator;

import java.util.NoSuchElementException;

@Service
public class LicensesServiceImpl implements LicensesService {

    public void addLicenseToDriver(PersonType driver, LicenseType license) {
        if (license.getLicenseNumber() == null) {
            license.setLicenseNumber(UUIDGenerator.generateString());
        }
        driver.getLicenses().getLicense().add(license);
    }

    public LicenseType findDriversLicenseById(PersonType driver, String licenseId) {
        return driver.getLicenses().getLicense().stream()
                .filter(licenseType -> licenseType.getLicenseNumber().equals(licenseId))
                .findAny().orElseThrow(NoSuchElementException::new);
    }

    public void removeLicenseFromDriverById(PersonType driver, String licenseId) {
        driver.getLicenses().getLicense().removeIf(licenseType -> licenseType.getLicenseNumber().equals(licenseId));
    }
}
