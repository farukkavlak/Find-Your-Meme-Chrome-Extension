import axios from "axios";

//Get image requests number
export const getImageRequests = async (currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "admin/getImageRequests";
    try {
        const response = await axios.get(url, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        return response;
    }catch (error) {
        return error.response;
    }
}

//Get images number
export const getImages = async (currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "admin/getImages";
    try {
        const response = await axios.get(url, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        return response;
    }catch (error) {
        return error.response;
    }
}

//Get tags number
export const getTags = async (currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL; 
    const url = baseUrl + "admin/getTags";
    try {
        const response = await axios.get(url, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        return response;
    }catch (error) {
        return error.response;
    }
}

//Get users 
export const getUsers = async (currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = baseUrl + "admin/getUsers";
    try {
        const response = await axios.get(url, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        return response;
    }catch (error) {
        return error.response;
    }

}

//Verify image
export const verifyImage = async (imageId,currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = `${baseUrl}admin/change-verification?imageId=${imageId}&status=true`;
    try {
        const response = await axios.patch(url,null, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        return response;
    }catch (error) {
        return error.response;
    }


}



//Delete image or image request
export const deleteImage = async (imageId,currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = `${baseUrl}admin/deleteImage?imageId=${imageId}`;
    try {
        const response = await axios.delete(url, {
            headers: {
                "Authorization": "Bearer " + currentToken
            },
        });
        return response;
    }catch (error) {
        console.log(error);
        return error.response;
    }
}

//change user role
export const changeRole = async (userId,currentToken,newRole) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const url = `${baseUrl}admin/changeRole?userId=${userId}&role=${newRole}`;
    try {
        const response = await axios.patch(url,null, {
            headers: {
                "Authorization": "Bearer " + currentToken,
            }
        });
        return response;
    }catch (error) {
        console.log(error);
        return error.response;
    }
}

export const getStats = async (currentToken) => {
    const baseUrl = process.env.REACT_APP_BASE_URL;
    const urlOne = baseUrl + "admin/requestNumber";
    const urlTwo = baseUrl + "admin/imageNumber";
    const urlThree = baseUrl + "admin/tagNumber";
    try {
        let response = null;
        //Pass authorization token to the request
        const responseOne = await axios.get(urlOne, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        const responseTwo = await axios.get(urlTwo, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        const responseThree = await axios.get(urlThree, {
            headers: {
                "Authorization": "Bearer " + currentToken
            }
        });
        response = {
            status: 200,
            requestNumber: responseOne.data.data,
            imageNumber: responseTwo.data.data,
            tagNumber: responseThree.data.data
        }
        return response;
    }catch (error) {
        return error.response;
    }
}

