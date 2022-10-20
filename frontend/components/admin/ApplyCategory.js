export default function ApplyCategory({ targetVal, setTargetVal }) {
  const makeDiscount = async () => {
    console.log(typeof startDate);
    console.log(startDate);
    await axios.post("/api/coupons/discount", {
      categoryId: null,
      productId: null,
      name: promotionName,
      discount: discount,
      discountType: "PRODUCT_DISCOUNT",
      startedAt: `${startDate} 00:00:00`,
      expiredAt: `${endDate} 00:00:00`,
    });
  };

  return (
    <>
      <div className="outter">
        <div>
          <p>카테고리</p>
          <ul>
            <li>
              <input
                type="radio"
                value="1"
                checked={targetVal === "1"}
                onChange={(e) => {
                  setTargetVal(e.target.value);
                }}
              />
              카테고리1
            </li>
            <li>
              <input
                type="radio"
                value="2"
                checked={targetVal === "2"}
                onChange={(e) => {
                  setTargetVal(e.target.value);
                }}
              />
              카테고리2
            </li>
            <li>
              <input
                type="radio"
                value="3"
                checked={targetVal === "3"}
                onChange={(e) => {
                  setTargetVal(e.target.value);
                }}
              />
              카테고리3
            </li>
          </ul>
        </div>
      </div>

      <style jsx>{`
        .outter {
          display: flex;

          p {
            border: 1px solid;
            margin: 0;
            text-align: center;
          }

          ul {
            margin: 0;
            padding: 0;
            list-style: none;
            border: 1px solid;
            text-align: left;

            li {
              padding: 0 100px 0 0;
              border-bottom: 1px solid #dedede;

              [type="radio"] {
                appearance: none;
                border: max(2px, 0.1em) solid gray;
                border-radius: 50%;
                width: 1em;
                height: 1em;
                transition: border 0.5s ease-in-out;
              }
            }

            li:last-child {
              border: 0;
            }
          }

          .check {
            ul {
              margin: 0;
              padding: 0;
              list-style: none;
              border: 1px solid;
              text-align: center;

              li {
                padding: 0 50px;
                border-bottom: 1px solid #dedede;
              }

              li:last-child {
                border: 0;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
