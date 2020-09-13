package com.example.crimemanagementapp.api;

import androidx.annotation.Nullable;

import com.example.crimemanagementapp.model.accounts.LoginResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeScenePicturesDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeScenePicturesModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedByPublicModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedDefaultResponse;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedSceneDefaultResponse;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedScenePicturesModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.model.miscellaneous.PasswordResetModel;
import com.example.crimemanagementapp.model.public_user_details.PublicUserDefaultResponse;
import com.example.crimemanagementapp.model.public_user_details.PublicUserModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeLiveUpdationModel;
import com.example.crimemanagementapp.model.cases_information.CrimeRegisterModel;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsDefaultResponse;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface Api {

    @GET("public_users_api/public_user_list_api/")
    Call<PublicUserDefaultResponse> getAllPublicUser();


    @GET("public_users_api/public_user_detail_api/{email}/")
    Call<PublicUserDefaultResponse> getDetailPublicUser(
            @Header("X-USERNAME")String user_name,
            @Path("email") String email);

    @GET("public_users_api/public_user_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailPublicUserAddress(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id") int resident_id);


    @PUT("public_users_api/public_user_detail_api/{email}/")
    Call<PublicUserDefaultResponse> putDetailPublicUser(
                              @Header("X-USERNAME")String user_name,
                              @Path("email") String email,@Body PublicUserModel User);

    @PUT("public_users_api/public_user_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailPublicUserAddress(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id")int resident_id,@Body AddressObject addressObject);


    //public user delete funs
    @DELETE("public_users_api/public_user_detail_api/{email}/")
    Call<DeleteObject>  deletePublicUserFacility(
            @Header("X-USERNAME")String user_name,
            @Path("email") String email);

    @DELETE("public_users_api/public_user_address_detail_api/{resident_id}/")
    Call<DeleteObject> deletePublicUserAddressFacility(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id")int resident_id);




    @POST("public_users_api/public_user_address_list_api/")
    Call<AddressObjectDefaultResponse> publicUserAddressRegisterFacility(@Body AddressObject addressObject);

    @POST("password_reset/forgot_password/")
    Call<PasswordResetModel> investigatorPasswordReset(@Body PasswordResetModel User);
    @POST("password_reset/password_otp_verify/")
    Call<PasswordResetModel> investigatorOTPVerify(@Body PasswordResetModel User);
    @POST("password_reset/password_update/")
    Call<PasswordResetModel> investigatorUpdatePassword(@Body PasswordResetModel User);

//investigator delete funs

    @Headers({"Content-Type:application/json"})
    @DELETE("crime_manage/investigator_detail_api/{email}/")
    Call<DeleteObject> deleteInvestigator(@Path("email") String email,
                                          @Header("X-USERNAME")String user_name,
                                          @Header("Authorization") String Token);


    @DELETE("crime_manage/investigator_address_detail_api/{resident_id}/")
    Call<DeleteObject> deleteInvestigatorAddress(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("resident_id") int resident_id);

    @FormUrlEncoded
    @POST("accounts/login/")
    Call<LoginResponse> Login(@Nullable @Field("password")String password,
                              @Nullable @Field("email")String email);

    @GET("crime_manage/investigator_list/")
    Call<InvestigatorDefaultResponse> getAllInvestigatorProfile();

    @POST("crime_manage/investigator_list/")
    Call<InvestigatorDefaultResponse> createInvestigatorProfile(@Body InvestigatorRegisterModel Investigator);

    @POST("crime_manage/investigator_address_list/")
    Call<AddressObjectDefaultResponse> investigatorAddressRegisterFacility(@Body AddressObject addressObject);




    @Headers({"Content-Type:application/json"})
    @GET("crime_manage/investigator_detail_api/{email}/")
    Call<InvestigatorDefaultResponse> getInvestigatorProfile(@Path("email") String email,
                                                             @Header("Authorization") String Token,
                                                             @Header("X-USERNAME")String user_name);



    @GET("crime_manage/investigator_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailInvestigatorAddress(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("resident_id") int resident_id);


    @Headers({"Content-Type:application/json"})
    @PUT("crime_manage/investigator_detail_api/{email}/")
    Call<InvestigatorDefaultResponse> updateInvestigatorProfile(@Path("email") String email,
                                                              @Header("X-USERNAME")String user_name,
                                                              @Header("Authorization") String Token,
                                                              @Body InvestigatorRegisterModel updateObj );

    @PUT("crime_manage/investigator_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailInvestigatorAddress(@Header("X-USERNAME") String user_name,
                                                                    @Header("Authorization") String Token,
                                                                    @Path("resident_id") int resident_id,
                                                                    @Body AddressObject addressObject);



    @POST("crime_manage/investigator_administrative_list/")
    Call<InvestigatorDefaultResponse> createInvestigatorAdministrativeFaciltiy(@Body InvestigatorAdministrativeInformationModel obj);

    @GET("crime_manage/investigator_detail_admin_api/{pk}/")
    Call<InvestigatorDefaultResponse> getInvestigatorAdministrativeFaciltiy(@Path("pk") int pk);


    @GET("crime_manage/investigator_administrative_list/")
    Call<InvestigatorDefaultResponse> getAllInvestigatorAdministrativeFaciltiy();

    @PUT("crime_manage/investigator_detail_admin_api/{pk}/")
    Call<InvestigatorDefaultResponse> putInvestigatorAdministrativeFaciltiy(@Path("pk") int pk,@Body InvestigatorAdministrativeInformationModel obj);

    @GET("crime_manage/investigator_detail_admin_get_api/{email}/")
    Call<InvestigatorDefaultResponse> getInvestigatorAdministrativeByEmailFaciltiy(@Path("email") String email);



    @DELETE("crime_manage/investigator_detail_admin_api/{pk}/")
    Call<DeleteObject> deleteInvestigatorAdministrativeFaciltiy(@Path("pk") int pk);





    @POST("crime_scene_pictures/crime_scene_images_list/")
    Call<CrimeScenePicturesDefaultResponse> crimeSceneImageRegisterFacility(@Header("X-USERNAME")String user_name,
                                                                            @Header("Authorization") String Token,
                                                                            @Body CrimeScenePicturesModel crimeScenePicturesModel);

    @GET("crime_scene_pictures/crime_scene_images_detail/{pk}/")
    Call<CrimeScenePicturesDefaultResponse> crimeSceneGetDetailImage(@Header("X-USERNAME")String user_name,
                                                                            @Header("Authorization") String Token,
                                                                  @Path("pk") int pk
                                                                          );
    @DELETE("crime_scene_pictures/crime_scene_images_detail/{pk}/")
    Call<DeleteObject> deleteCrimeSceneImage(@Header("X-USERNAME")String user_name,
                                                                  @Header("Authorization") String Token,
                                                                  @Path("pk") int pk
    );
    @GET("crime_scene_pictures/crime_scene_images/{crime_id}/")
    Call<CrimeScenePicturesDefaultResponse> crimeSceneImageGetAll(@Header("X-USERNAME")String user_name,
                                                                  @Header("Authorization") String Token,
                                                                  @Path("crime_id") int crime_id
    );


    @GET("cases_info/crime_update_live/")
    Call<CrimeDefaultResponse> crimeLiveUpdateGetFacility( @Header("X-USERNAME")String user_name,
                                                           @Header("Authorization") String Token);

    @POST("cases_info/crime_update_live/")
    Call<CrimeDefaultResponse> crimeLiveUpdatePostFacility( @Header("X-USERNAME")String user_name,
                                                            @Header("Authorization") String Token,
                                                            @Body CrimeLiveUpdationModel crimeLiveUpdationModel);

    @GET("cases_info/crime_update_live_detail/{pk}/")
    Call<CrimeDefaultResponse> crimeLiveUpdateGetFacility(  @Header("X-USERNAME")String user_name,
                                                            @Header("Authorization") String Token,
                                                            @Path("pk") int pk);

    @PUT("cases_info/crime_update_live_detail/{pk}/")
    Call<CrimeDefaultResponse> crimeLiveUpdatePutFacility(  @Header("X-USERNAME")String user_name,
                                                            @Header("Authorization") String Token,
                                                            @Path("pk") int pk,
                                                            @Body CrimeLiveUpdationModel crimeLiveUpdationModel);



    @DELETE("cases_info/crime_update_live_detail/{pk}/")
    Call<DeleteObject> deleteCrimeLiveUpdateFacility( @Header("X-USERNAME")String user_name,
                                                   @Header("Authorization") String Token,
                                                   @Path("pk") int pk);




    @GET("cases_info/crime_list/")
    Call<CrimeDefaultResponse> crimeRegisterGETFacility(@Header("X-USERNAME") String user_name,
                                                        @Header("Authorization") String Token);

    @POST("cases_info/crime_list/")
    Call<CrimeDefaultResponse> crimeRegisterFacility( @Header("X-USERNAME")String user_name,
                                                      @Header("Authorization") String Token,
                                                      @Body CrimeRegisterModel crimeRegisterModel);
    @GET("cases_info/crime_detail_api/{pk}/")
    Call<CrimeDefaultResponse> crimeUpdateGetFacility( @Header("X-USERNAME")String user_name,
                                                       @Header("Authorization") String Token,
                                                       @Path("pk") int pk);

    @PUT("cases_info/crime_detail_api/{pk}/")
    Call<CrimeDefaultResponse> crimeUpdatePutFacility( @Header("X-USERNAME")String user_name,
                                                       @Header("Authorization") String Token,
                                                       @Path("pk") int pk,
                                                       @Body  CrimeRegisterModel crimeRegisterModel);

    //delete fun
    @DELETE("cases_info/crime_detail_api/{pk}/")
    Call<DeleteObject> deleteCrimeFacility( @Header("X-USERNAME")String user_name,
                                                       @Header("Authorization") String Token,
                                                       @Path("pk") int pk);

    @DELETE("cases_info/crime_location_address_detail_api/{resident_id}/")
    Call<DeleteObject> deleteCrimeAddressFacility( @Header("X-USERNAME")String user_name,
                                                              @Header("Authorization") String Token,
                                                              @Path("resident_id") int resident_id);



    @POST("cases_info/crime_location_address_list/")
    Call<AddressObjectDefaultResponse> crimeAddressRegisterFacility( @Header("X-USERNAME")String user_name,
                                                                     @Header("Authorization") String Token,
                                                                     @Body AddressObject addressObject);


    @GET("cases_info/crime_location_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailCrimeAddress( @Header("X-USERNAME")String user_name,
                                                              @Header("Authorization") String Token,
                                                              @Path("resident_id") int resident_id);


    @PUT("cases_info/crime_location_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailCrimeAddress( @Header("X-USERNAME")String user_name,
                                                              @Header("Authorization") String Token,
                                                              @Path("resident_id") int resident_id,
                                                              @Body AddressObject addressObject);



    @POST("criminal_victim_details/victim_list/")
    Call<VictimCriminalDefaultResponse> victimRegisterFacility(@Header("X-USERNAME")String user_name,
                                                               @Header("Authorization") String Token,
                                                               @Body VictimCriminalRegisterModel victimCriminalRegisterModel);

    @GET("criminal_victim_details/victim_list/")
    Call<VictimCriminalDefaultResponse> victimGETFacility(@Header("X-USERNAME")String user_name,
                                                          @Header("Authorization") String Token);

    @GET("criminal_victim_details/victim_detail_api/{pk}/")
    Call<VictimCriminalDefaultResponse> victimGETDetailFacility(@Header("X-USERNAME")String user_name,
                                                                @Header("Authorization") String Token,
                                                                @Path("pk") int pk);

    @PUT("criminal_victim_details/victim_detail_api/{pk}/")
    Call<VictimCriminalDefaultResponse> victimPutDetailFacility(@Header("X-USERNAME")String user_name,
                                                                @Header("Authorization") String Token,
                                                                @Path("pk") int pk,
                                                                @Body VictimCriminalRegisterModel victimCriminalRegisterModel);
//vicitim delete fun
    @DELETE("criminal_victim_details/victim_detail_api/{pk}/")
    Call<DeleteObject> victimDeleteFacility(@Header("X-USERNAME")String user_name,
                                            @Header("Authorization") String Token,
                                            @Path("pk") int pk);


    @DELETE("criminal_victim_details/victim_address_detail_api/{resident_id}/")
    Call<DeleteObject>   deleteVictimAddress(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("resident_id") int resident_id);



    @POST("criminal_victim_details/criminal_list/")
    Call<VictimCriminalDefaultResponse> criminalRegisterFacility( @Header("X-USERNAME")String user_name,
                                                                  @Header("Authorization") String Token,
                                                                  @Body VictimCriminalRegisterModel victimCriminalRegisterModel);

    @GET("criminal_victim_details/criminal_list/")
    Call<VictimCriminalDefaultResponse> criminalGETFacility(@Header("X-USERNAME")String user_name,
                                                            @Header("Authorization") String Token);

    @GET("criminal_victim_details/criminal_detail_api/{pk}/")
    Call<VictimCriminalDefaultResponse> criminalGETDetailFacility(@Header("X-USERNAME")String user_name,
                                                                  @Header("Authorization") String Token,
                                                                  @Path("pk") int pk);

    @PUT("criminal_victim_details/criminal_detail_api/{pk}/")
    Call<VictimCriminalDefaultResponse> criminalPutDetailFacility(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk,
            @Body VictimCriminalRegisterModel victimCriminalRegisterModel);

    // criminal delete fun

    @DELETE("criminal_victim_details/criminal_detail_api/{pk}/")
    Call<DeleteObject> criminalDeleteFacility(@Header("X-USERNAME")String user_name,
                                              @Header("Authorization") String Token,
                                              @Path("pk") int pk);

    @DELETE("criminal_victim_details/criminal_address_detail_api/{resident_id}/")
    Call<DeleteObject> deleteCriminalAddress(@Header("X-USERNAME")String user_name,
                                             @Header("Authorization") String Token,
                                             @Path("resident_id") int resident_id);


//addresses
    @POST("criminal_victim_details/criminal_address_list/")
    Call<AddressObjectDefaultResponse> criminalAddressRegisterFacility(@Header("X-USERNAME")String user_name,
                                                                       @Header("Authorization") String Token,
                                                                       @Body AddressObject addressObject);


    @GET("criminal_victim_details/criminal_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailCriminalAddress(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("resident_id") int resident_id);


    @PUT("criminal_victim_details/criminal_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailCriminalAddress(@Header("X-USERNAME")String user_name,
                                                                @Header("Authorization") String Token,
                                                                    @Path("resident_id") int resident_id,
                                                                    @Body AddressObject addressObject);



    @POST("criminal_victim_details/vicitm_address_list/")
    Call<AddressObjectDefaultResponse> victimAddressRegisterFacility(@Header("X-USERNAME")String user_name,
                                                                     @Header("Authorization") String Token,
                                                                     @Body AddressObject addressObject);


    @GET("criminal_victim_details/victim_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailVictimAddress(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("resident_id") int resident_id);


    @PUT("criminal_victim_details/victim_address_detail_api/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailVictimAddress(@Header("X-USERNAME")String user_name,
                                                              @Header("Authorization") String Token,
                                                                @Path("resident_id") int resident_id,
                                                                @Body AddressObject addressObject);


    @GET("crime_reporting/crime_reported_list/")
    Call<CrimeReportedDefaultResponse> getAllCrimeReporedAllFacility(@Header("X-USERNAME")String user_name);

    @GET("crime_reporting/crime_reported_detail/{id}/")
    Call<CrimeReportedDefaultResponse> getDetailCrimeReporedFacility(@Header("X-USERNAME")String user_name,
                                                                     @Path("id") int id);

    @PUT("crime_reporting/crime_reported_detail/{id}/")
    Call<CrimeReportedDefaultResponse> PutCrimeReporedFacility(@Header("X-USERNAME")String user_name,
                                                               @Path("id") int id,
                                                               @Body CrimeReportedByPublicModel crimeReportedByPublicModel);
// crime reported delete fun
    @DELETE("crime_reporting/crime_reported_detail/{id}/")
    Call<DeleteObject> deleteCrimeReporedFacility(@Header("X-USERNAME")String user_name,
                                                               @Path("id") int id);
    @DELETE("crime_reporting/crime_reported_address_detail/{resident_id}/")
    Call<DeleteObject> deletetCrimeReportedAddress(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id") int resident_id);



    @GET("crime_reporting/crime_reported_address_detail/{resident_id}/")
    Call<AddressObjectDefaultResponse> getDetailCrimeReportedAddress(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id") int resident_id);

    @PUT("crime_reporting/crime_reported_address_detail/{resident_id}/")
    Call<AddressObjectDefaultResponse> putDetailCrimeReportedAddress(
            @Header("X-USERNAME")String user_name,
            @Path("resident_id") int resident_id,
            @Body AddressObject addressObject);



    @POST("investigator_contact_us/qurey_list/")
    Call<InvestigatorContactUsDefaultResponse> registerQueryFacility(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Body InvestigatorContactUsModel ContactUsModel);

    @GET("investigator_contact_us/qurey_list/")
    Call<InvestigatorContactUsDefaultResponse> getAllQueryFacilityOfInvestigator(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token);


    @GET("public_user_contact_us/qurey_list/")
    Call<InvestigatorContactUsDefaultResponse> getAllQueryFacilityOfPublicUser(
            @Header("X-USERNAME")String user_name);



    @GET("public_user_contact_us/query_detail_api/{pk}/")
    Call<InvestigatorContactUsDefaultResponse> getAllDetailQueryFacilityOfPublicUser(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk);


    @PUT("public_user_contact_us/query_detail_api/{pk}/")
    Call<InvestigatorContactUsDefaultResponse> putAllDetailQueryFacilityOfPublicUser(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk,
            @Body InvestigatorContactUsModel ContactUsModel
    );
    @DELETE("public_user_contact_us/query_detail_api/{pk}/")
    Call<DeleteObject> deleteQueryFacilityOfPublicUser(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk
    );



    @GET("investigator_contact_us/query_detail_api/{pk}/")
    Call<InvestigatorContactUsDefaultResponse> getAllDetailQueryFacilityOfInvestigator(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk);


    @PUT("investigator_contact_us/query_detail_api/{pk}/")
    Call<InvestigatorContactUsDefaultResponse> putAllDetailQueryFacilityOfInvestigator(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk,
            @Body InvestigatorContactUsModel ContactUsModel
    );

    @DELETE("investigator_contact_us/query_detail_api/{pk}/")
    Call<DeleteObject> deleteQueryFacilityOfInvestigator(
            @Header("X-USERNAME")String user_name,
            @Header("Authorization") String Token,
            @Path("pk") int pk
    );


    @POST("crime_reporting_scene_pictures/crime_reporting_scene_images_list/")
    Call<CrimeReportedSceneDefaultResponse> crimeReportingSceneImageRegisterFacility(@Header("X-USERNAME")String user_name,
                                                                                     @Body CrimeReportedScenePicturesModel crimeReportedScenePicturesModel);

    @GET("crime_reporting_scene_pictures/crime_reporting_scene_images/{crime_id}/")
    Call<CrimeReportedSceneDefaultResponse> crimeReportedSceneImageGetAll(@Header("X-USERNAME")String user_name,
                                                                          @Path("crime_id") int crime_id
    );
    @GET("crime_reporting_scene_pictures/crime_reporting_scene_images_detail/{pk}/")
    Call<CrimeReportedSceneDefaultResponse> crimeReportedSceneGetDetailImage(@Header("X-USERNAME")String user_name,
                                                                             @Path("pk") int pk
    );
    @DELETE("crime_reporting_scene_pictures/crime_reporting_scene_images_detail/{pk}/")
    Call<DeleteObject> deleteCrimeReportedSceneImage(@Header("X-USERNAME")String user_name,
                                                     @Path("pk") int pk);

}

