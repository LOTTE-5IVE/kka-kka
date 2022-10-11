export default function ApplyGrade() {
  return (
    <>
      <div className="outter">
        <div>
          <ul>
            <li>
              <input type="checkbox" />
              등급1
            </li>
            <li>
              <input type="checkbox" />
              등급2
            </li>
            <li>
              <input type="checkbox" />
              등급3
            </li>
            <li>
              <input type="checkbox" />
              등급4
            </li>
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
              padding: 0 100px 0 0;
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
