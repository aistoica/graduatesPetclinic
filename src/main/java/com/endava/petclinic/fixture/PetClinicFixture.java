package com.endava.petclinic.fixture;

import org.apache.http.HttpStatus;

import com.endava.petclinic.clients.OwnerClient;
import com.endava.petclinic.clients.UserClient;
import com.endava.petclinic.data.DataGenerator;
import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.RoleName;
import com.endava.petclinic.models.User;

import io.restassured.response.Response;

public class PetClinicFixture {

	private DataGenerator dataGenerator = new DataGenerator();
	private UserClient userClient = new UserClient();
	private OwnerClient ownerClient = new OwnerClient();

	private User user;
	private Owner owner;

	public PetClinicFixture createUser( RoleName... roleNames ) {

		user = dataGenerator.getUser( roleNames );
		Response response = userClient.createUser( user );
		response.then().statusCode( HttpStatus.SC_CREATED );

		return this;
	}

	public PetClinicFixture createOwner() {

		owner = dataGenerator.getOwner();
		Response response = ownerClient.createOwner( owner, user );
		response.then().statusCode( HttpStatus.SC_CREATED );

		int ownerId = response.jsonPath().getInt( "id" );
		owner.setId( ownerId );

		return this;
	}

	public User getUser() {
		return user;
	}

	public Owner getOwner() {
		return owner;
	}
}
