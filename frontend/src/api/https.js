import axios from "axios";

// axios 객체 생성
export default axios.create({
    URL: "",
    headers: {
        "Content-type": "application/json"
    }
})