import { Table } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import Swal from "sweetalert2";
import { useAuth } from "../contexts/AuthContext";
import { getTags } from "../functions/AdminFunctions";
import styles from "./tables.module.css";


const TagsTable = (props) => {;
    const [data, setData] = useState([]);
    const dataSource = data;
    const { currentToken } = useAuth();
    const columns = [
        {
            title: "Tag",
            dataIndex: 'name',
            key: 'name',
        },
    ];
    async function getTagsHelper() {
        const response = await getTags(currentToken);
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
    useEffect(() => {
        getTagsHelper().then((res) => {});
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div>
            <Table className={styles.Line} dataSource={dataSource} columns={columns} />
        </div>
    );
}

export default TagsTable;
