graph[
    node[
        id 1
        label "Which age group does the patient belong to?"
        display "radio"
    ]
    edge[
        source 1
        target 2
        label "18 to 49 years old"
    ]
    edge[
        source 1
        target 2
        label "50 years or older"
    ]


    node[
        id 100
        label "Does the patient suffer from any of the following conditions?"
        display "checkbox"
    ]
    edge[
        source 2
        target 3
        label "diabetes"
    ]
    edge[
        source 2
        target 3
        label "hypertension"
    ]
    edge[
        source 2
        target 3
        label "chronic pain"
    ]


    node[
            id 3
            label "exit"
            display "terminate"
     ]



    node[
        id 2
        label "Does the patient have a sore throat?"
        display "radio"
    ]
    edge[
        source 2
        target 3
        label "yes"
    ]
    edge[
        source 2
        target 3
        label "no"
    ]





]

