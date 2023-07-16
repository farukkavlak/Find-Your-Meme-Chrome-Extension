import { Table } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import Swal from "sweetalert2";
import { useAuth } from "../contexts/AuthContext";
import { deleteImage, getImages } from "../functions/AdminFunctions";
import styles from "./tables.module.css";


const ImagesTable = (props) => {;
    const [data, setData] = useState([]);
    const dataSource = data;
    const { currentToken } = useAuth();
    const columns = [
        {
            title: "ID",
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: "Image",
            dataIndex: 'image_path',
            key: 'image_path',
            render: (text, record) => {
                return <img src={record.imagePath} alt={record.id} className={styles.image} />
            }
        },
        {
            title: "Tags",
            dataIndex: 'tags',
            key: 'tags',
            render: (text, record) => {
                return <ul>
                    {record.tags.map((tag) => {
                        return <li>{tag}</li>
                    })}
                </ul>
            }
        },
        {
            title: "Status",
            render: (text, record) => {
                return <div>
                    <button className={styles.buttonTable} onClick={() => deleteImageHelper(record.id)}>Delete</button>
                </div>
            }
        }
    ];
    async function getImagesHelper() {
        const response = await getImages(currentToken);
        if(response.status !== 200) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        }else {
            let data = response.data.data;
            setData(data);
        }
    }
    async function deleteImageHelper(imageId) {
        const response = await deleteImage(imageId,currentToken);
        if(response.status !== 200) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        }else {
            setData(data.filter((item) => item.id !== imageId));
        }
    }
    useEffect(() => {
        getImagesHelper().then((res) => {});
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div>
            <Table className={styles.Line} dataSource={dataSource} columns={columns} />
        </div>
    );
}

export default ImagesTable;
