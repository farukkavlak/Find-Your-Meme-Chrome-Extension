import { Button, Input, Pagination } from "antd";
import { useState } from "react";
import axios from "axios";
import styles from "../styles/Home.module.css";
import Swal from "sweetalert2";
import { colorEnum } from "../util/colorEnum";
import { findByTag } from "../functions/Functions";

const MemeGrid = () => {
    const [tag, setTag] = useState("")
    const [images, setImages] = useState(null)
    const [pageNumber, setPageNumber] = useState(0)
    const [activePage, setActivePage] = useState(0)

    const getImagesByTag = async (tag, page, size) => {
        await findByTag(tag, page, size).then((res) => {
            console.log(res)
            if (res.status !== 200) {
                 Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: res.data.data.message,
                })
                return
            }
            setPageNumber(res.data.data.pageNumber)
            setImages(res.data.data.images)
        })
    }
    const handleTagClick = async (e) => {
        let tag = e.target.innerText
        await getImagesByTag(tag, 0, 8)
    }
    return (
        <div>
            <div style={{ display: "flex", justifyContent: "center", marginBottom: 16 }}>
                <Input
                    placeholder=""
                    value={tag}
                    onChange={(e) => setTag(e.target.value)}
                    style={{ width: "60%", marginRight: 16, maxWidth: "100px" }}
                />
                <Button
                    onClick={async () => {
                        //If tag size less than 3, return error
                        if (tag.trim().length < 3) {
                            alert("Please enter at least 3 characters")
                            return
                        }
                        //Pagination, page size = 8
                        await getImagesByTag(tag, 0, 8);
                    }}
                >
                    Search
                </Button>
            </div>
            <div className={styles.gallery}>
                {
                    images && images.map((image, index) => {
                        return (
                            <div className={styles.card}>
                                <img src={image.imagePath} alt="Snow" style={{ width: "100%" }} />
                                <div>
                                    {
                                        image.tags.map((tagOutput, index) => {
                                            return (
                                                <span className={styles.tag} onClick={handleTagClick} style={{ backgroundColor: colorEnum[index] }}>{tagOutput}</span>
                                            )
                                        })
                                    }
                                </div>
                            </div>
                        )
                    })
                }
            </div>
            {
                images && (<Pagination defaultCurrent={activePage + 1} total={pageNumber} onChange={(page) => {
                    setActivePage(page - 1)
                    getImagesByTag(tag, page - 1, 8)
                }} />)
            }

        </div>
    )
}

export default MemeGrid