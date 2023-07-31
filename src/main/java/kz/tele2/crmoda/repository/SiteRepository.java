package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer> {

    Site getSiteByName(String name);

}
