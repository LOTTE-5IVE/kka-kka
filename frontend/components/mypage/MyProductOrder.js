import {commaMoney} from "../../hooks/commaMoney";
import Review from "./review/Review";
import {useEffect, useState} from "react";

export default function MyProductOrder({ productOrder }) {
  const [reviewed, setReviewed] = useState(productOrder.review != null);

  const setReviewedState = () => {
    setReviewed(true);
  };

  useEffect(() => {}, [reviewed]);

  console.log(productOrder)

  return (
    <>
      <div className="wrapper">
        <div className="d-flex justify-space-between">
          <div className="d-flex flex-column align-start w-100">
            <div className="d-flex align-start">
              <div className="title-label">상품주문번호</div>
              <div className="title-id">{productOrder.id}</div>
            </div>
            <div className="product-info w-100">
              <img
                className="mt-3"
                src={productOrder.product.imageUrl}
                alt={productOrder.product.name}
              />
              <div className="d-flex justify-space-between w-100">
                <div className="d-flex flex-column align-start product-wrapper">
                  <p className="w-100">{productOrder.product.name}</p>
                  <div className="w-100">
                    <span className="detail">
                      수량: {productOrder.quantity}
                    </span>
                  </div>
                  <div className="price-info">
                    <div className="d-flex justify-space-between detail-price">
                      <div>
                        상품 가격
                      </div>
                      <div>
                        {commaMoney(productOrder.price * productOrder.quantity)} 원
                      </div>
                    </div>
                    <div className="d-flex justify-space-between detail-discount">
                      <div>
                        기본 할인
                      </div>
                      <div>
                        - {commaMoney(
                          (productOrder.price *
                            (0.01 * productOrder.discount)) * productOrder.quantity,
                        ) || 0}{" "}
                        원
                      </div>
                    </div>
                    <div className="d-flex justify-space-between detail-discount">
                      <div>
                        쿠폰 할인 {productOrder.coupon == null || `(${productOrder.coupon.name})`}
                      </div>
                      <div>
                        - {productOrder.coupon ? commaMoney(
                          productOrder.coupon.discountedPrice
                      ) : 0}{" "}
                        원
                      </div>
                    </div>
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
            <>
            <div className="divider"></div>
            <Review
              productOrderId={productOrder.id}
              setReviewed={setReviewedState}
            />
            </>
        )}
      </div>
      <style jsx>
        {`
          @media screen and (min-width: 769px) {
            .product-info {
              display: flex;
              justify-content: center;
              align-items: start;
            }
            
            .product-wrapper {
              width: 100%;
            }
            
            .price-info {
              width: 70%;
            }
          }
          
          @media screen and (max-width: 768px) {
            .product-info {
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: start;
            }
            
            .product-wrapper {
              width: 100%;
            }
            
            .price-info {
              width: 80%;
            }
          }
          
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
            width: 180px;
            height: 180px;
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
            margin-bottom: 0.5rem;
          }
                    
          .detail-price {
            color: #3e3e3e;
            font-size: 1rem;
            margin: 0.5rem 0 1rem 0; 
          }
          
          .detail-discount {
            color: #5e5e5e;
            font-size: 0.9rem;
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
          
          .w-100 {
            width: 100%;
          }
          
          .w-80 {
            width: 80%;
          }
          
          .w-50 {
            width: 50%;
          }
          
          .divider {
              border-bottom: 1.1px solid #eeeeee;
              margin-top: 2rem;
              width: 100%;
          }
        `}
      </style>
    </>
  );
}
