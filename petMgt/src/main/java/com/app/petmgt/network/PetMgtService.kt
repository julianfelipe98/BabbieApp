package com.app.petmgt.network


import com.app.base.model.network.petMgt.Breed
import com.app.base.model.network.petMgt.Pet
import com.app.base.model.network.petMgt.Species
import com.app.petmgt.utils.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface PetMgtService {

    @GET("/pets/user/{id}")
    suspend fun getPets(@Path(value = "id") userId: String): List<Pet>

    @GET("/species/")
    suspend fun getSpecies(): List<Species>

    @GET("/breeds/specie/{id}")
    suspend fun getBreedBySpecies(@Path(value = "id") specieId: String): List<Breed>

    @Multipart
    @POST("/pets/uploadPhoto/")
    suspend fun setImage(
        @Part file: MultipartBody.Part,
        @Part("filename") filename: RequestBody
    ): Response<JSONObject>

    @Multipart
    @PUT("/pets/updatePhoto/{idPet}")
    suspend fun updateImage(
        @Part file: MultipartBody.Part,
        @Part("mime_type") mime_type: RequestBody,
        @Path(value = "idPet") petId: String
    ): Response<JSONObject>

    @POST("/pets/")
    suspend fun createPet(@Body pet: Pet): ResponseModel

    @GET("/pets/{id}")
    suspend fun getPet(@Path(value = "id") petId: String): Pet

    @PUT("/pets/{id}")
    suspend fun updatePet(@Body pet: Pet, @Path(value = "id") petId: String): Response<JSONObject>

}