export default function ApplyGrade({ grade, setGrade }) {
  const grades = ["VIP", "GOLD", "SILVER", "BRONZE"];

  return (
    <>
      <div className="outter">
        <div>
          <ul>
            {grades?.map((grd, index) => {
              return (
                <li key={index}>
                  <input
                    type="radio"
                    value={grd}
                    checked={grade == grd}
                    onChange={(e) => {
                      setGrade(e.target.value);
                    }}
                  />
                  {grd}
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
