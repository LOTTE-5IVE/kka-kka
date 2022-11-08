import { commaMoney } from "../../hooks/commaMoney";
import Review from "./review/Review";
import { useEffect, useState } from "react";

export default function MyProductOrder({ productOrder }) {
  const [reviewed, setReviewed] = useState(productOrder.review != null);

  const setReviewedState = () => {
    setReviewed(true);
  };

  useEffect(() => {}, [reviewed]);

  return (
    <>
      <div className="wrapper">
        <div className="d-flex justify-space-between">
          <div className="d-flex flex-column align-start">
            <div className="d-flex align-start">
              <div className="title-label">상품주문번호</div>
              <div className="title-id">{productOrder.id}</div>
            </div>
            <div className="d-flex align-start">
              <img className="mt-3" src={productOrder.product.imageUrl} />
              <div className="d-flex flex-column">
                <div className="d-flex flex-column align-start">
                  <p>{productOrder.product.name}</p>
                </div>
                <div className="d-flex flex-column">
                  <div>
                    <span className="detail">
                      수량: {productOrder.quantity}
                    </span>
                  </div>
                  <div className="d-flex justify-space-between">
                    <span className="detail">
                      상품가격: {commaMoney(productOrder.product.price)} 원
                    </span>
                    <span className="detail">|</span>
                    <span className="detail">
                      할인가격:{" "}
                      {commaMoney(
                        productOrder.product.price *
                          (0.01 * productOrder.product.discount),
                      ) || 0}{" "}
                      원
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="reviewed">
            {reviewed && <span>후기작성완료</span>}
          </div>
        </div>
        {!reviewed && (
          <Review
            productOrderId={productOrder.id}
            setReviewed={setReviewedState}
          />
        )}
      </div>
      <style jsx>
        {`
          .wrapper {
            margin: 1rem 0 0.5rem 0;
            padding: 0.3rem;
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

          .justify-space-between {
            justify-content: space-between;
          }

          .productorder-container {
            width: 100%;
          }

          img {
            width: 110px;
            height: 110px;
            object-fit: contain;
            padding: 0;
            border: 1px solid #eaeaea;
            border-radius: 10px;
            margin-right: 1rem;
          }

          .mt-3 {
            margin-top: 0.3rem;
          }

          .title-label {
            font-size: 0.9rem;
            color: #3e3e3e;
            margin-right: 0.2rem;
          }

          .title-id {
            font-size: 0.9rem;
            font-weight: bold;
            color: #3e3e3e;
          }

          .detail {
            color: #3e3e3e;
            font-size: 0.9rem;
            margin-right: 1rem;
          }

          .detail-btn {
            color: #8b8b8b;
            text-decoration: underline;
            text-underline-color: #a7a7a7;
            font-size: 0.9rem;

            cursor: pointer;
            max-width: 80px;

            -webkit-touch-callout: none;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
          }

          .detail-content {
            padding-inline: 0;
          }

          .reviewed {
            width: fit-content;
            font-size: 0.8rem;
            color: #3e3e3e;
            margin-top: auto;
            margin-bottom: auto;
          }
        `}
      </style>
    </>
  );
}
