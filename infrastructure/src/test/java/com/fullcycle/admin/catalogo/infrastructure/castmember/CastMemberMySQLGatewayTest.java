package com.fullcycle.admin.catalogo.infrastructure.castmember;

import com.fullcycle.admin.catalogo.MySQLGatewayTest;
import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberType;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberMySQLGateway;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.fullcycle.admin.catalogo.domain.Fixture.CastMembers.type;
import static com.fullcycle.admin.catalogo.domain.Fixture.name;

@MySQLGatewayTest
class CastMemberMySQLGatewayTest {

    @Autowired
    private CastMemberMySQLGateway castMemberMySqlGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @BeforeEach
    void cleanUp() {
        this.castMemberRepository.deleteAll();
    }

    @Test
    public void testDependecies() {
        Assertions.assertNotNull(castMemberMySqlGateway);
        Assertions.assertNotNull(castMemberRepository);
    }

    @Test
    public void ginvenAValidCastMember_whenCallsCreate_shouldPersistIt() {
        // given

        final var expectedName = name();

        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        Assertions.assertEquals(0, castMemberRepository.count());

//        when
        final var actualMember = castMemberMySqlGateway.create(CastMember.with(aMember));

//        then
        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

        final var persisted = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persisted.getId());
        Assertions.assertEquals(expectedName, persisted.getName());
        Assertions.assertEquals(expectedType, persisted.getType());
        Assertions.assertEquals(actualMember.getCreatedAt(), persisted.getCreatedAt());
        Assertions.assertEquals(actualMember.getUpdatedAt(), persisted.getUpdatedAt());
    }

    @Test
    public void ginvenAValidCastMember_whenCallsUpdate_shouldRefreshIt() throws InterruptedException {

        // given

        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        final var aMember = CastMember.newMember("Vin Diesel", CastMemberType.DIRECTOR);
        final var expectedId = aMember.getId();

        final var aPreCreatedMember = castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals("Vin Diesel", aPreCreatedMember.getName());
        Assertions.assertEquals(CastMemberType.DIRECTOR, aPreCreatedMember.getType());

        Thread.sleep(1);

        final var clone = CastMember.with(aMember);

        final var updated = clone.update(expectedName, expectedType);
//        when
        final var actualMember = castMemberMySqlGateway.update(updated);

//        then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(actualMember.getUpdatedAt()));

        final var persisted = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persisted.getId());
        Assertions.assertEquals(expectedName, persisted.getName());
        Assertions.assertEquals(expectedType, persisted.getType());
        Assertions.assertEquals(actualMember.getCreatedAt(), persisted.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(persisted.getUpdatedAt()));

    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeleteIt() {
//        given

        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
//        when

        castMemberMySqlGateway.deleteById(aMember.getId());
//        then

        Assertions.assertEquals(0, castMemberRepository.count());

    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteById_shouldBeIgnored() {
//        given

        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
//        when

        castMemberMySqlGateway.deleteById(CastMemberID.from("123"));
//        then

        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    public void givenAValidCastMember_whenCallsFindById_shouldReturnIt() {

//        given

        final var expectedName = name();

        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
//when

        final var actualMember = castMemberMySqlGateway.findById(expectedId).get();


//        then

        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

    }

    @Test
    void givenAnInvalidId_whenCallsFindById_ShouldReturnEmpty() {

//        given

        final var aMember = CastMember.newMember(name(), type());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
//when

        final var actualMember = castMemberMySqlGateway.findById(CastMemberID.from("132"));


//        then

        Assertions.assertTrue(actualMember.isEmpty());

    }

    @Test
    public void givenEmptyCastMembers_whenCallsFindAll_shouldReturnEmpty() {
//        given

        final var expectedPage = 0;

        final var expectedPerPage = 10;

        final var expectedTerms = "";

        final var expectedSort = "name";

        final var expectedDirection = "asc";

        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

//        when

        final var actualPage = castMemberMySqlGateway.findAll(aQuery);

//        then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());

        Assertions.assertEquals(expectedPerPage, actualPage.perPage());

        Assertions.assertEquals(expectedTotal, actualPage.items().size());

    }

    @ParameterizedTest
    @CsvSource({"vin,0,10,1,1,Vin Diesel", "taran,0,10,1,1,Quentin Tarantino", "jas,0,10,1,1,Jason Momoa", "Mar,0,10,1,1,Martin Scorsese", "KIT,0,10,1,1,Kit Harington",})
    public void givenAValidTerm_WhenCallsFindAll_shouldReturnFiltered(final String expectedTerms, final int expectedPage, final int expectedPerPage, final int expectedItemsCount, final long expectedTotal, final String expectedName) {

        mockMembers();

        final var expectedSort = "name";

        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        //        when

        final var actualPage = castMemberMySqlGateway.findAll(aQuery);

//        then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());

        Assertions.assertEquals(expectedPerPage, actualPage.perPage());

        Assertions.assertEquals(expectedTotal, actualPage.total());

        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());

        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());


    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,5,5,Jason Momoa",
            "name,desc,0,10,5,5,Vin Diesel",
            "createdAt,asc,0,10,5,5,Kit Harington",
            "createdAt,desc,0,10,5,5,Martin Scorsese"
    })
    public void givenAValidSortAndDirection_whenCallsFindAll_shouldReturnSorted(final String expectedSort, final String expectedDirection, final int expectedPage, final int expectedPerPage, final int expectedItemsCount, final long expectedTotal, final String expectedName) {


        mockMembers();

        final var expectedTerms = "";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        //        when

        final var actualPage = castMemberMySqlGateway.findAll(aQuery);

//        then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());

        Assertions.assertEquals(expectedPerPage, actualPage.perPage());

        Assertions.assertEquals(expectedTotal, actualPage.total());

        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());

        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({"0,2,2,5,Jason Momoa;Kit Harington", "1,2,2,5,Martin Scorsese;Quentin Tarantino", "2,2,1,5,Vin Diesel",})
    public void givenAValidPagination_whenCallsFindAll_shouldReturnPaginated(final int expectedPage, final int expectedPerPage, final int expectedItemsCount, final long expectedTotal, final String expectedNames) {


        mockMembers();

        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        //        when

        final var actualPage = castMemberMySqlGateway.findAll(aQuery);

//        then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());

        Assertions.assertEquals(expectedPerPage, actualPage.perPage());

        Assertions.assertEquals(expectedTotal, actualPage.total());

        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());

        int index = 0;

        for (final var expectedName : expectedNames.split(";")) {
            Assertions.assertEquals(expectedName, actualPage.items().get(index).getName());
            index++;
        }

    }

    @Test
    public void givenTwoCastMembersAndOnePersisted_whenCallsExistsByIds_shouldReturnPersistedID() {
        // given
        final var aMember = CastMember.newMember("Vin", CastMemberType.DIRECTOR);

        final var expectedItems = 1;
        final var expectedId = aMember.getId();

        Assertions.assertEquals(0, castMemberRepository.count());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        // when
        final var actualMember = castMemberMySqlGateway.existsByIds(List.of(CastMemberID.from("123"), expectedId));

        // then
         Assertions.assertEquals(expectedItems, actualMember.size());
        Assertions.assertEquals(expectedId.getValue(), actualMember.get(0).getValue());
    }


    private void mockMembers() {

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(CastMember.newMember("Kit Harington", CastMemberType.ACTOR)));
        waitToCreate();
        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(CastMember.newMember("Vin Diesel", CastMemberType.ACTOR)));
        waitToCreate();
        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(CastMember.newMember("Quentin Tarantino", CastMemberType.DIRECTOR)));
        waitToCreate();
        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(CastMember.newMember("Jason Momoa", CastMemberType.ACTOR)));
        waitToCreate();
        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(CastMember.newMember("Martin Scorsese", CastMemberType.DIRECTOR)));

    }

    private void waitToCreate() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {
        }
    }
}