import { Table } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import Swal from "sweetalert2";
import { useAuth } from "../contexts/AuthContext";
import {changeRole, getUsers } from "../functions/AdminFunctions";
import styles from "./tables.module.css";


const UsersTable = (props) => {;
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
            title: "Username",
            dataIndex: 'username',
            key: 'username',
        },
        {
            title: "Role",
            dataIndex: 'role',
            key: 'role',
        },
        {
            title: "Status",
            render: (text, record) => {
                return <div>
                    <button className={styles.buttonTableChangeRole} onClick={() => changeRoleHelpers(record.id, record.role)}>{record.role === "ADMIN" ? "Make User" : "Make Admin"}</button>
                </div>
            }
        }
    ];

    async function changeRoleHelpers(id, role) {
        const newRole = role === "ADMIN" ? "USER" : "ADMIN";
        const response = await changeRole(id, currentToken, newRole);
        if(response.status !== 200) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        }else {
            setData(data.map((user) => {
                if(user.id === id) {
                    user.role = newRole;
                }
                return user;
            }))
        }

    }
    async function getUsersHelper() {
        const response = await getUsers(currentToken);
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
        getUsersHelper().then((res) => {});
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div>
            <Table className={styles.Line} dataSource={dataSource} columns={columns} />
        </div>
    );
}

export default UsersTable;
