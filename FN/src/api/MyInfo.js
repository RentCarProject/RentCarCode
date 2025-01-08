
import { UserApi } from "./UserAPI";



export const fetchUserInfo = async () => {
  try {
    const response = await UserApi.get("/api/v1/user"); 
    return response.data; 
  } catch (error) {
    console.error("Error fetching user info:", error);
    throw error;
  }
};
