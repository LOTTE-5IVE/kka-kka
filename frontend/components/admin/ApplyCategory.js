export default function ApplyCategory({ targetVal, setTargetVal }) {
  const cat_name = [
    "비스킷/샌드",
    "스낵/봉지과자",
    "박스과자",
    "캔디/사탕/젤리",
    "시리얼/바",
    "초콜릿",
    "껌/자일리톨",
    "선물세트",
  ];

  return (
    <>
      <div className="outter">
        <div>
          <p>카테고리</p>
          <ul>
            {cat_name.map((category, index) => {
              return (
                <li key={index}>
                  <input
                    type="radio"
                    value={index + 1}
                    checked={targetVal == index + 1}
                    onChange={(e) => {
                      setTargetVal(e.target.value);
                    }}
                  />
                  {category}
                </li>
              );
            })}
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
              padding: 5px 100px 5px 0;
              border-bottom: 1px solid #dedede;
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
