export default function Nutri({ nutrition }) {
  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>영양성분표</th>
            <th>열량(kcal)</th>
            <th>탄수화물(g)</th>
            <th>당류(g)</th>
            <th>단백질(g)</th>
            <th>지방(g)</th>
            <th>포화지방(g)</th>
            <th>트랜스지방(g)</th>
            <th>콜레스테롤(mg)</th>
            <th>나트륨(mg)</th>
            <th>칼슘(mg)</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th>1회 제공량 기준</th>
            <td>{nutrition.calorie}</td>
            <td>{nutrition.carbohydrate}</td>
            <td>{nutrition.sugar}</td>
            <td>{nutrition.protein}</td>
            <td>{nutrition.fat}</td>
            <td>{nutrition.saturated_fat}</td>
            <td>{nutrition.trans_fat}</td>
            <td>{nutrition.cholesterol}</td>
            <td>{nutrition.sodium}</td>
            <td>{nutrition.calcium}</td>
          </tr>
          <tr>
            <th>일일권장량 대비(%)</th>
            <td></td>
            <td>{Math.round((nutrition.carbohydrate / 324) * 100)} %</td>
            <td>{Math.round((nutrition.sugar / 100) * 100)} %</td>
            <td>{Math.round((nutrition.protein / 55) * 100)} %</td>
            <td>{Math.round((nutrition.fat / 54) * 100)} %</td>
            <td>{Math.round((nutrition.saturated_fat / 15) * 100)} %</td>
            <td></td>
            <td>{Math.round((nutrition.cholesterol / 300) * 100)} %</td>
            <td>{Math.round((nutrition.sodium / 2000) * 100)} %</td>
            <td>{Math.round((nutrition.calcium / 700) * 100)} %</td>
          </tr>
        </tbody>
      </table>
      <style jsx>{`
        table {
          border: 1px solid #acacac;
          border-collapse: collapse;
          text-align: center;
          margin: 0 auto 3rem;
          font-color: #101010;
        }
        th,
        td {
          border: 1px solid #acacac;
          padding: 0.5rem;
        }
        td {
          font-size: 0.9rem;
        }
        th {
          background-color: #dfdfdf;
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          div {
            width: 85vw;
            overflow-x: scroll;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          div {
            width: 450px;
            overflow-x: scroll;
          }
        }
      `}</style>
    </div>
  );
}

Nutri.defaultPros = {};
