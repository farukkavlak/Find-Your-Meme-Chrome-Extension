import axios from 'axios';

export const findByTag = async (tag, page, size) => {
    const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
    let url = `${baseUrl}/by-tags?tag=${tag}&page=${page}&size=${size}`
    try {
        const res = await axios.get(url);
        return res;
    } catch (err) {
        return err.response;
    }    
}

export const uploadImage = async (image,tags) => {
    const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
    let url = `${baseUrl}/save`
    const formData = new FormData();
    const tagsForm = tags.join(',');
    formData.append('image', image);
    formData.append('tags', tagsForm);
    try {
        const res = await axios.post(url, formData);
        return res;
    } catch (err) {
        return err.response;
    }
}  