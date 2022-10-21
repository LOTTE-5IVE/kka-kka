import {useEffect, useRef, useState} from "react";
import RangeWithIcons from "./RangeWithIcons";
import useNoticeToInputPreference from "./useInputRating";
import {PostHApi} from "../../../apis/Apis";
import {useGetToken} from "../../../hooks/useGetToken";

export default function Review({productOrderId, setReviewed}) {
  const [token, setToken] = useState("");
  const [rating, setRating] = useState(0.5);
  const [contents, setContents] = useState("");
  const preferenceRef = useRef(null);

  const {setIsBlinked} = useNoticeToInputPreference({
    targetRef: preferenceRef,
  });

  const setRatingValue = (value) => {
    setRating(value);
  };

  const onRatingStart = () => {
    setIsBlinked(false);
  }

  const onWriteContents = (event) => {
    setContents(event.target.value);
  }

  const handleSubmit = async () => {
    PostHApi(`/api/reviews?productOrder=${productOrderId}`,
      {
        rating: rating,
        contents: contents
      }, token).then(() => { setReviewed(true) });
  };

  useEffect(() => {
    setToken(useGetToken());
  }, [token]);

  return (
    <>
      <div className="wrapper">
        <div className="d-flex flex-column align-center">
          <span className="detail">상품은 어떠셨나요?</span>
          <RangeWithIcons
            labelText="선호도 입력"
            color={'#ffd151'}
            max={5}
            min={0.5}
            step={0.5}
            value={rating}
            setValue={setRatingValue}
            onStart={onRatingStart}
          />
          <textarea value={contents} onChange={onWriteContents}/>
          <span className="submitBtn" onClick={() => handleSubmit()}>
            상품후기 등록하기
          </span>
        </div>
      </div>
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
            width: 70%;
            min-height: 60px;
            border: 1px solid #c5c5c5;
            border-radius: 10px;
            padding: 0.2rem;
            font-size: 0.8rem;
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
        `}
      </style>
    </>
  )
}