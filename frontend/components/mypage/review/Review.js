import { useEffect, useRef, useState } from "react";
import RangeWithIcons from "./RangeWithIcons";
import useNoticeToInputPreference from "./useInputRating";
import { PostHApi } from "../../../apis/Apis";
import { getToken } from "../../../hooks/getToken";
import axios from "axios";

export default function Review({ productOrderId, setReviewed }) {
  const [token, setToken] = useState("");
  const [rating, setRating] = useState(0.5);
  const [contents, setContents] = useState("");
  const preferenceRef = useRef(null);

  const { setIsBlinked } = useNoticeToInputPreference({
    targetRef: preferenceRef,
  });

  const setRatingValue = (value) => {
    setRating(value);
  };

  const onRatingStart = () => {
    setIsBlinked(false);
  };

  const onWriteContents = (event) => {
    setContents(event.target.value);
  };

  const handleSubmit = async () => {
    if (contents.length < 5) {
      alert("글자 수가 너무 적습니다.");
      return;
    }

    await axios.post(
      `/api/reviews?productOrder=${productOrderId}`,
      {
        rating: rating,
        contents: contents.slice(0, 100),
      },
      {
        headers:{
          "Authorization": `Bearer ${token}`
        }
      },
    ).then((res) => {
      const reviewId = res.headers.location
      setReviewed(reviewId);
    })
  };

  useEffect(() => {
    setToken(getToken());
  }, [token]);

  return (
    <>
      <span className="detail">상품은 어떠셨나요?</span>
      <RangeWithIcons
        labelText="선호도 입력"
        color={"#ffd151"}
        max={5}
        min={0.5}
        step={0.5}
        value={rating}
        setValue={setRatingValue}
        onStart={onRatingStart}
        borderColor={"#ffd151"}
        starWidth={"25px"}
      />
      <textarea
        value={contents}
        cols="5"
        rows="3"
        placeholder="5글자 이상 100글자 이하로 입력해주세요."
        maxLength={100}
        onChange={onWriteContents}
      />
      <p>
        <span>({contents.slice(0, 100).length} / 100자)</span>
      </p>

      <span className="submitBtn" onClick={() => handleSubmit()}>
        상품후기 등록하기
      </span>
      <style jsx>
        {`
          .wrapper {
            width: 100%;
            margin-top: 1rem;
            padding: 0.5rem;
          }

          .d-flex {
            display: flex;
            justify-content: center;
          }

          .flex-column {
            flex-direction: column;
          }

          .align-center {
            align-items: center;
          }

          .align-start {
            align-items: start;
          }

          .detail {
            color: #3e3e3e;
            font-size: 0.8rem;
            font-weight: bold;
          }

          textarea {
            width: 50%;
            min-height: 60px;
            border: 1px solid #c5c5c5;
            border-radius: 10px;
            padding: 0.2rem;
            font-size: 0.8rem;
          }

          p {
            width: 50%;
            text-align: right;
            margin: 0;
          }

          .submitBtn {
            padding: 0.3rem;
            color: white;
            background: #f34444;
            text-align: center;
            cursor: pointer;
            font-size: 0.8rem;
            border-radius: 5px;
            margin-top: 1rem;
          }

          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            textarea {
              width: 80%;
              border-radius: 10px;
            }
            p {
              width: 80%;
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            textarea {
              width: 80%;
              border-radius: 10px;
            }
            p {
              width: 80%;
            }
          }
        `}
      </style>
    </>
  );
}
