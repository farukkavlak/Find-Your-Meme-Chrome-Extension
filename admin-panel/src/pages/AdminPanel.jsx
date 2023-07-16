import { useState } from "react";
import styles from './AdminPanel.module.css'
import { Button, Col, Menu, Row } from "antd";
import { useEffect } from "react";
import ImageRequestTable from "../components/ImageRequestsTable";
import Login from "../components/Login";
import Register from "../components/Register";
import { useAuth } from "../contexts/AuthContext";
import { getStats } from "../functions/AdminFunctions";
import Swal from "sweetalert2";
import ImagesTable from "../components/ImagesTable";
import TagsTable from "../components/TagsTable";
import UsersTable from "../components/UsersTable";

function getItem(label, key, description, role, icon, children, type) {
    return {
        key,
        icon,
        description,
        role,
        children,
        label,
        type,
    };
}


const AdminPanel = () => {
    const { currentToken,setCurrentToken } = useAuth();
    const [current, setCurrent] = useState("-1");
    const [items, setItems] = useState([]);
    const [stats, setStats] = useState();
    const menuClicked = async (e) => {
        if(e.key === "6"){
            setCurrentToken(null);
            setCurrent("-1");
            return;
        }
        setCurrent(e.key);
    };

    const closeCurrent = () => {
        setCurrent("-1");
    };
    useEffect(() => {
        if (currentToken === null) {
            setItems([
                getItem("Login", "4", "Login to the system", "admin", "", null, "item"),
                getItem("Register", "5", "Register to the system", "admin", "", null, "item"),
            ])
        } else {
            getStats(currentToken).then((res) => {
                if(res.status !== 200) {
                    Swal.fire({
                        title: 'Error!',
                        text: 'Something went wrong',
                        icon: 'error',
                        confirmButtonText: 'Cool'
                    })
                }else {
                    setStats(res);
                }
            })
            setItems([
                getItem("Image Requests", "0", "View all image requests", "admin", "", null, "item"),
                getItem("Images", "1", "View all images", "admin", "", null, "item"),
                getItem("Tags", "2", "View all tags", "admin", "", null, "item"),
                getItem("Users", "3", "View all users", "admin", "", null, "item"),
                getItem("Logout", "6", "Logout from the system", "admin", "", null, "item"),
            ])
        }
    }, [currentToken]);
    const reloadRemaining = () => {
        getStats(currentToken).then((res) => {
            if(res.status !== 200) {
                Swal.fire({
                    title: 'Error!',
                    text: 'Something went wrong',
                    icon: 'error',
                    confirmButtonText: 'Cool'
                })
            }else {
                setStats(res);
            }
        });
    }
    return (
        <div className={styles.recordPageContainer}>
            {
                currentToken !== null &&
                <Col className={styles.voiceNumber}>
                <Row>
                    <h2 className={styles.recordPageTitle}>Image Requests: {stats?.requestNumber}</h2>
                    <h2 className={styles.recordPageTitle}>Images: {stats?.imageNumber}</h2>
                    <h2 className={styles.recordPageTitle}>Tags: {stats?.tagNumber}</h2>
                </Row>
                <Row>
                    <Button type="primary" onClick={reloadRemaining}>Yenile</Button>
                </Row>
            </Col>
            }
            <Row wrap={true} className={styles.menuContainer} justify='center'>
                <Col xxl={6} xl={6} lg={6} md={6} sm={22} xs={22} className={styles.menuColumn}>
                    <Menu
                        onClick={menuClicked}
                        className={styles.menu}
                        defaultOpenKeys={['sub1']}
                        selectedKeys={[current]}
                        mode="inline"
                        items={items}
                    />
                </Col>
                <Col xxl={17} xl={17} lg={17} md={17} sm={22} xs={23} className={styles.tableColumn}>
                    <div className={styles.recordContainer}>
                        {current === "0" && <ImageRequestTable />}
                        {current === "1" && <ImagesTable/> }
                        {current === "2" && <TagsTable/> }
                        {current === "3" && <UsersTable/> }
                        {current === "4" && <Login closeCurrent={closeCurrent} />}
                        {current === "5" && <Register closeCurrent={closeCurrent} />}
                    </div>
                </Col>
            </Row>
        </div>
    )
}
export default AdminPanel