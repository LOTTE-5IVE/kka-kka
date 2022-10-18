export default function ApplyCategory() {
  return (
    <>
      <div className="outter">
        <div>
          <p>카테고리</p>
          <ul>
            <li>
              <input type="checkbox" />
              카테고리1
            </li>
            <li>
              <input type="checkbox" />
              카테고리2
            </li>
            <li>
              <input type="checkbox" />
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
