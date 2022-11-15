export default function ApplyGrade({ targetVal, setTargetVal }) {
  const grades = ["GOLD", "SILVER", "BRONZE"];

  return (
    <>
      <div className="outter">
        <div>
          <ul>
            {grades?.map((grade, index) => {
              return (
                <li key={index}>
                  <input
                    type="radio"
                    value={grade}
                    checked={targetVal == grade}
                    onChange={(e) => {
                      setTargetVal(e.target.value);
                    }}
                  />
                  {grade}
                </li>
              );
            })}
          </ul>
        </div>
      </div>

      <style jsx>{`
        .outter {
          display: flex;

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
